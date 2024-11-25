package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.*;
import com.futbol.Fantasy.service.FootballerService;
import com.futbol.Fantasy.service.LeagueService;
import com.futbol.Fantasy.service.PlayerLeagueService;
import com.futbol.Fantasy.service.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Controller
public class CreateLeagueController {

    @Autowired
    LeagueService leagueService;

    @Autowired
    FootballerService footballerService;

    @Autowired
    PlayerLeagueService playerLeagueService;

    @Autowired
    PlayerService playerService;

    @FXML
    private TextField leagueNameField;

    Player playerLogged;

    @FXML
    public void handleCreate() throws IOException {
        if(!leagueNameField.getText().isEmpty() && !leagueNameField.getText().contains("#")) {
            League league = new League();
            league.setName(leagueNameField.getText());

            List<Footballer> allFootballers = footballerService.findAll();
            List<Footballer> footballersMarket = selectMarketFootballers(allFootballers, 15);

            league.setFootballersMarket(footballersMarket);
            allFootballers.removeAll(footballersMarket);

            Player player = playerService.findById(playerLogged.getId());
            PlayerLeague playerLeague = leagueService.saveLeagueWithPlayer(league, player, "playing");


            Map<String, List<Footballer>> classifiedFootballers = classifyFootballersByPosition(allFootballers);
            List<PlayerLeagueFootballer> playerLeagueFootballers = new ArrayList<>();

            playerLeagueFootballers.addAll(assignFootballers(classifiedFootballers.get("PORTERO"), playerLeague, 2, 1));
            playerLeagueFootballers.addAll(assignFootballers(classifiedFootballers.get("DEFENSA"), playerLeague, 7, 4));
            playerLeagueFootballers.addAll(assignFootballers(classifiedFootballers.get("CENTROCAMPISTA"), playerLeague, 7, 4));
            playerLeagueFootballers.addAll(assignFootballers(classifiedFootballers.get("DELANTERO"), playerLeague, 4, 2));

            playerLeague.setPlayerLeagueFootballers(playerLeagueFootballers);
            playerLeagueService.save(playerLeague);

            FantasyApplication.showPlayerMenuScene(playerLogged);
            Stage stage = (Stage) this.leagueNameField.getScene().getWindow();

            stage.close();
        }
    }
    public void initData(Player loggedUser) {
        playerLogged = loggedUser;
        System.out.println(loggedUser.getUserName());
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

    private List<PlayerLeagueFootballer> assignFootballers(List<Footballer> footballerList, PlayerLeague playerLeague, int count, int starters) {
        List<PlayerLeagueFootballer> playerLeagueFootballers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            if (footballerList.isEmpty()) break;
            Footballer footballer = footballerList.get(random.nextInt(footballerList.size()));
            PlayerLeagueFootballer playerLeagueFootballer = new PlayerLeagueFootballer();
            playerLeagueFootballer.setFootballer(footballer);
            playerLeagueFootballer.setPlayerLeague(playerLeague);
            playerLeagueFootballer.setSelected(i < starters);
            playerLeagueFootballers.add(playerLeagueFootballer);
            footballerList.remove(footballer);
        }
        return playerLeagueFootballers;
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

}