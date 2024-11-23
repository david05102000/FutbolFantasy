package com.futbol.Fantasy.controller;


import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.*;
import com.futbol.Fantasy.service.PlayerLeagueService;
import com.futbol.Fantasy.table.model.FootballerTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;


@Component
public class MenuController {

    @FXML
    public TableView<FootballerTableView> playerTableView;

    @FXML
    public TableColumn<FootballerTableView, String> nameColumn;

    @FXML
    public TableColumn<FootballerTableView, String> pointsColumn;

    @FXML
    public TableView<FootballerTableView> myTeamTableView;

    @FXML
    public TableColumn<FootballerTableView, ImageView> teamShieldColumnTeam;

    @FXML
    public TableColumn<FootballerTableView, String> teamName;

    @FXML
    public TableColumn<FootballerTableView, String> footballerNameTeam;

    @FXML
    public TableColumn<FootballerTableView, String> positionTeam;

    @FXML
    public TableView<FootballerTableView> marketTableView;

    @FXML
    public TableColumn<FootballerTableView, ImageView> teamShieldColumn;

    @FXML
    public TableColumn<FootballerTableView, String> footballerNameColumn;

    @FXML
    public TableColumn<FootballerTableView, FootballerTableView> actionColumn;

    @FXML
    public Label moneyAvailable;


    @Autowired
    PlayerLeagueService playerLeagueService;

    List<PlayerLeague> playerLeagueList;

    PlayerLeague playerLeagueLogged;

    private Long leagueId;
    private Player playerLogged;

    @FXML
    private void initialize() {
    }
    @Transactional
    public void initData(Long leagueId, Player playerLogged, TableView<FootballerTableView> playerTableView, TableColumn<FootballerTableView, String> nameColumn, TableColumn<FootballerTableView, String> pointsColumn, TableColumn<FootballerTableView, ImageView> teamShieldColumn, TableColumn<FootballerTableView, FootballerTableView> actionColumn, TableView<FootballerTableView> myTeamTableView, TableColumn<FootballerTableView, ImageView> teamShieldColumnTeam, TableColumn<FootballerTableView, String> teamName, TableColumn<FootballerTableView, String> footballerNameTeam, TableColumn<FootballerTableView, String> positionTeam, Label money) {
        this.leagueId = leagueId;
        this.playerLogged = playerLogged;
        this.playerTableView = playerTableView;
        this.nameColumn = nameColumn;
        this.pointsColumn = pointsColumn;
        this.teamShieldColumn = teamShieldColumn;
        this.actionColumn = actionColumn;
        this.myTeamTableView = myTeamTableView;
        this.teamShieldColumnTeam = teamShieldColumnTeam;
        this.teamName = teamName;
        this.footballerNameTeam = footballerNameTeam;
        this.positionTeam = positionTeam;
        this.moneyAvailable = money;
        loadPlayers();
        loadMyTeam();

    }

    private void loadMyTeam() {
        playerLeagueLogged = playerLeagueService.findByLeagueIdAndPlayerId(leagueId, playerLogged.getId());

        playerLeagueList = playerLeagueService.findByLeagueId(leagueId);
        ObservableList<PlayerLeagueFootballer> footballers = FXCollections.observableArrayList(playerLeagueLogged.getPlayerLeagueFootballers());

        ObservableList<FootballerTableView> footballerTableViewList = FXCollections.observableArrayList();

        footballers.forEach(f -> {
            ImageView imageView = new ImageView(f.getFootballer().getTeam().getPhoto());
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            footballerTableViewList.add(new FootballerTableView(imageView, f.getFootballer().getName(), f.getFootballer().getRol(), f.getFootballer().getTeam().getName()));
        });
        teamShieldColumnTeam.setCellValueFactory(new PropertyValueFactory<>("photo"));
        footballerNameTeam.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamName.setCellValueFactory(new PropertyValueFactory<>("team"));
        positionTeam.setCellValueFactory(new PropertyValueFactory<>("rol"));



        myTeamTableView.setItems(footballerTableViewList);

        Map<String, Integer> roleOrder = new HashMap<>();
        roleOrder.put("PORTERO", 1);
        roleOrder.put("DEFENSA", 2);
        roleOrder.put("CENTROCAMPISTA", 3);
        roleOrder.put("DELANTERO", 4);

        positionTeam.setComparator(Comparator.comparingInt(roleOrder::get));

        myTeamTableView.getSortOrder().add(positionTeam);
        positionTeam.setSortType(TableColumn.SortType.ASCENDING);
        myTeamTableView.sort();
    }

    private void loadPlayers() {
        playerLeagueList = playerLeagueService.findByLeagueId(leagueId);

        ObservableList<Player> players = FXCollections.observableArrayList();

        ObservableList<FootballerTableView> playersTableViewList = FXCollections.observableArrayList();

        playerLeagueList.forEach(p -> {
            playersTableViewList.add(new FootballerTableView(p.getPoints(), p.getPlayer().getUserName()));
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        playerTableView.setItems(playersTableViewList);

        playerTableView.getSortOrder().add(pointsColumn);
        pointsColumn.setSortType(TableColumn.SortType.DESCENDING);
        playerTableView.sort();
    }


    @FXML
    public void handleLeagues() throws IOException {
        FantasyApplication.showPlayerMenuScene(playerLogged);
    }
    @FXML
    public void handleSignOut() throws IOException {
        FantasyApplication.showLoginScene();
    }


}