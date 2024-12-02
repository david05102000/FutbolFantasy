package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.*;
import com.futbol.Fantasy.service.FootballerService;
import com.futbol.Fantasy.service.LeagueService;
import com.futbol.Fantasy.service.MarketOfferService;
import com.futbol.Fantasy.service.PlayerLeagueService;
import com.futbol.Fantasy.table.model.MarketAdd;
import com.futbol.Fantasy.utils.WebScraper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminMenuController {

    @Autowired
    private WebScraper webScraper;

    @Autowired
    MarketOfferService marketOfferService;
    @Autowired
    FootballerService footballerService;
    @Autowired
    PlayerLeagueService playerLeagueService;
    @Autowired
    LeagueService leagueService;

    @FXML
    public void handleInsertTeamsAndFootballers() {
        try {
            webScraper.getTeamsAndFootballersAndSave();
            showSuccess("Plantillas insertadas correctamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showError("Ha ocurrido un error durante las inserción de las plantillas");
        }
    }


    @FXML
    public void handleUpdateMarket() throws IOException {
        try {

            List<Footballer> footballerList = footballerService.findAll();

            for (Footballer footballer : footballerList) {
                if (!footballer.getMarketOfferList().isEmpty()) {
                    List<MarketAdd> marketAddList = new ArrayList<>();
                    List<MarketAdd> payBackAmountList = new ArrayList<>();
                    for (MarketOffer marketOffer : footballer.getMarketOfferList()) {
                        if (!marketOffer.getExecuted()) {
                            Optional<MarketAdd> existingMarketAdd = marketAddList.stream()
                                    .filter(add -> add.getLeagueId().equals(marketOffer.getLeagueId()))
                                    .findFirst();

                            if (existingMarketAdd.isPresent()) {
                                MarketAdd add = existingMarketAdd.get();
                                if (marketOffer.getAmount() > add.getAmount()) {
                                    MarketAdd payBackAmount = new MarketAdd();
                                    payBackAmount.setPlayerId(add.getPlayerId());
                                    payBackAmount.setLeagueId(add.getLeagueId());
                                    payBackAmount.setAmount(add.getAmount());
                                    payBackAmountList.add(payBackAmount);
                                    add.setPlayerId(marketOffer.getPlayerId());
                                    add.setAmount(marketOffer.getAmount());
                                } else {
                                    MarketAdd payBackAmount = new MarketAdd();
                                    payBackAmount.setPlayerId(marketOffer.getPlayerId());
                                    payBackAmount.setLeagueId(marketOffer.getLeagueId());
                                    payBackAmount.setAmount(marketOffer.getAmount());
                                    payBackAmountList.add(payBackAmount);
                                }
                            } else {
                                MarketAdd newAdd = new MarketAdd();
                                newAdd.setFootballer(footballer);
                                newAdd.setLeagueId(marketOffer.getLeagueId());
                                newAdd.setPlayerId(marketOffer.getPlayerId());
                                newAdd.setAmount(marketOffer.getAmount());
                                marketAddList.add(newAdd);
                            }
                            marketOffer.setExecuted(true);
                            marketOfferService.insert(marketOffer);
                        }
                    }
                    for (MarketAdd marketAdd : marketAddList) {
                        PlayerLeague playerLeague = playerLeagueService.findByLeagueIdAndPlayerId(marketAdd.getLeagueId(), marketAdd.getPlayerId());
                        PlayerLeagueFootballer playerLeagueFootballer = new PlayerLeagueFootballer();
                        playerLeagueFootballer.setSelected(false);
                        playerLeagueFootballer.setFootballer(footballer);
                        playerLeagueFootballer.setPlayerLeague(playerLeague);
                        playerLeague.getPlayerLeagueFootballers().add(playerLeagueFootballer);
                        playerLeagueService.save(playerLeague);


                        List<Footballer> footballerAllList = footballerService.findAll();
                        List<PlayerLeague> allPlayersInLeague = playerLeagueService.findByLeagueId(playerLeague.getLeague().getId());
                        List<Footballer> footballersMarket = updatedMarket(footballerAllList, allPlayersInLeague);

                        League league = playerLeague.getLeague();
                        league.getFootballersMarket().clear();
                        league.setFootballersMarket(footballersMarket);

                        leagueService.save(league);
                    }

                    for (MarketAdd payBack : payBackAmountList) {
                        PlayerLeague playerLeague = playerLeagueService.findByLeagueIdAndPlayerId(payBack.getLeagueId(), payBack.getPlayerId());
                        playerLeague.setMoney(playerLeague.getMoney() + payBack.getAmount());
                        playerLeagueService.save(playerLeague);
                    }
                }
            }
            showSuccess("Mercado actualizado con éxito.");
        } catch (Exception e) {
            showError("No se ha podido actualizar el mercado.");
        }
    }

    @FXML
    public void handleGivePoints() throws IOException {
        try {
            FantasyApplication.showGivePointsMenu();
        }catch (Exception e){
            showError("No se han podido introducir los puntos.");
        }
    }

    @FXML
    public void handleLogOut() throws IOException {
        FantasyApplication.showLoginScene();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/alert.css").toExternalForm());
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/alert.css").toExternalForm());
        alert.showAndWait();
    }

    private List<Footballer> selectRandomFootballers(List<Footballer> footballerList, int count) {
        List<Footballer> selected = new ArrayList<>();
        Random random = new Random();
        while (selected.size() < count && !footballerList.isEmpty()) {
            Footballer footballer = footballerList.get(random.nextInt(footballerList.size()));
            if (!selected.contains(footballer)) {
                selected.add(footballer);
            }
        }
        return selected;
    }

    private List<Footballer> updatedMarket(List<Footballer> footballerList, List<PlayerLeague> allPlayersInLeague) {
        List<Footballer> availableFootballers = footballerList.stream()
                .filter(f -> allPlayersInLeague.stream()
                        .flatMap(pl -> pl.getPlayerLeagueFootballers().stream())
                        .noneMatch(plf -> plf.getFootballer().equals(f)))
                .collect(Collectors.toList());

        List<Footballer> updatedMarket = selectMarketFootballers(availableFootballers, 15);

        return updatedMarket;
    }

    private List<Footballer> selectMarketFootballers(List<Footballer> footballerList, int count) {
        Map<String, List<Footballer>> classified = classifyFootballersByPosition(footballerList);

        List<Footballer> selected = new ArrayList<>();
        selected.addAll(selectRandomFootballers(classified.get("PORTERO"), 2));
        selected.addAll(selectRandomFootballers(classified.get("DEFENSA"), 5));
        selected.addAll(selectRandomFootballers(classified.get("CENTROCAMPISTA"), 5));
        selected.addAll(selectRandomFootballers(classified.get("DELANTERO"), 3));

        return selected;
    }

    private Map<String, List<Footballer>> classifyFootballersByPosition(List<Footballer> footballers) {
        Map<String, List<Footballer>> classified = new HashMap<>();
        classified.put("PORTERO", new ArrayList<>());
        classified.put("DEFENSA", new ArrayList<>());
        classified.put("CENTROCAMPISTA", new ArrayList<>());
        classified.put("DELANTERO", new ArrayList<>());

        for (Footballer footballer : footballers) {
            classified.getOrDefault(footballer.getRol(), new ArrayList<>()).add(footballer);
        }
        return classified;
    }
}

