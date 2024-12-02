package com.futbol.Fantasy.controller;


import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.model.League;
import com.futbol.Fantasy.model.PlayerLeague;
import com.futbol.Fantasy.model.PlayerLeagueFootballer;
import com.futbol.Fantasy.service.FootballerService;
import com.futbol.Fantasy.service.LeagueService;
import com.futbol.Fantasy.service.PlayerLeagueService;
import com.futbol.Fantasy.service.PlayerService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InvitationMenuController {

    @Autowired
    LeagueService leagueService;

    @Autowired
    PlayerLeagueService playerLeagueService;

    @Autowired
    PlayerService playerService;

    @Autowired
    FootballerService footballerService;

    private Long playerLoggedId;

    @FXML
    public TableView<League> tableView;

    @FXML
    public TableColumn<League, String> leagueName;

    @FXML
    public TableColumn<League, League> buttons;

    @Transactional
    public void initData(Long playerLoggedId, TableView<League> tableView, TableColumn<League, String> leagueName, TableColumn<League, League>  buttons) {
        this.playerLoggedId = playerLoggedId;
        this.tableView = tableView;
        this.leagueName = leagueName;
        this.buttons = buttons;
        ObservableList<League> leagues = FXCollections.observableArrayList(leagueService.findAllLeaguesByPlayerAndStatus(playerLoggedId, "invited"));

        tableView.setItems(leagues);
        leagueName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        buttons.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        buttons.setCellFactory(param -> new TableCell<>() {
            final Button editButton = new Button("Aceptar");
            final Button deleteButton = new Button("Rechazar");

            final HBox hbox = new HBox(editButton, deleteButton);

            {
                editButton.setOnAction(this::handleAcceptButton);
                deleteButton.setOnAction(this::handleDeclineButton);
            }

            @Override
            protected void updateItem(League league, boolean empty) {
                super.updateItem(league, empty);

                if (league == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }

            @Transactional
            private void handleAcceptButton(ActionEvent event) {
                League league = getTableView().getItems().get(getIndex());
                for(PlayerLeague playerLeague: league.getPlayers()) {
                    if(playerLeague.getPlayer().getId().equals(playerLoggedId)) {

                        List<Footballer> footballerList = footballerService.findAll();
                        List<Footballer> marketFootballers = league.getFootballersMarket();
                        Set<Long> marketFootballerIds = marketFootballers.stream()
                                .map(Footballer::getId)
                                .collect(Collectors.toSet());


                        List<PlayerLeague> allPlayersInLeague = playerLeagueService.findByLeagueId(playerLeague.getLeague().getId());
                        Set<Long> assignedFootballerIds = allPlayersInLeague.stream()
                                .flatMap(player -> player.getPlayerLeagueFootballers().stream())
                                .map(plf -> plf.getFootballer().getId())
                                .collect(Collectors.toSet());

                        List<Footballer> availableFootballerList = footballerList.stream()
                                .filter(footballer -> !marketFootballerIds.contains(footballer.getId()))
                                .filter(footballer -> !assignedFootballerIds.contains(footballer.getId()))
                                .collect(Collectors.toList());


                        Map<String, List<Footballer>> classifiedFootballers = classifyFootballersByPosition(availableFootballerList);
                        List<PlayerLeagueFootballer> playerLeagueFootballers = new ArrayList<>();

                        playerLeagueFootballers.addAll(assignFootballers(classifiedFootballers.get("PORTERO"), playerLeague, 2, 1));
                        playerLeagueFootballers.addAll(assignFootballers(classifiedFootballers.get("DEFENSA"), playerLeague, 7, 4));
                        playerLeagueFootballers.addAll(assignFootballers(classifiedFootballers.get("CENTROCAMPISTA"), playerLeague, 7, 4));
                        playerLeagueFootballers.addAll(assignFootballers(classifiedFootballers.get("DELANTERO"), playerLeague, 4, 2));


                        playerLeague.setStatus("playing");
                        playerLeague.setPlayerLeagueFootballers(playerLeagueFootballers);
                        playerLeagueService.save(playerLeague);
                    }
                }
                getTableView().getItems().remove(league);
                try {
                    FantasyApplication.showPlayerMenuScene(playerService.findById(playerLoggedId));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Transactional
            private void handleDeclineButton(ActionEvent event) {
                League league = getTableView().getItems().get(getIndex());
                for(PlayerLeague playerLeague: league.getPlayers()) {
                    if(playerLeague.getPlayer().getId().equals(playerLoggedId)) {
                        playerLeagueService.delete(playerLeague);
                    }
                }
                getTableView().getItems().remove(league);
                try {
                    FantasyApplication.showPlayerMenuScene(playerService.findById(playerLoggedId));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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

        });
    }
}
