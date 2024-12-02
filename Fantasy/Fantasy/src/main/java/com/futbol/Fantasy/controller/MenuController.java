package com.futbol.Fantasy.controller;


import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.*;
import com.futbol.Fantasy.service.LeagueService;
import com.futbol.Fantasy.service.MarketOfferService;
import com.futbol.Fantasy.service.PlayerLeagueService;
import com.futbol.Fantasy.table.model.FootballerTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
    public TableColumn<FootballerTableView, ImageView> ShieldColumnTeam;

    @FXML
    public TableColumn<FootballerTableView, String> teamName;

    @FXML
    public TableColumn<FootballerTableView, String> footballerNameTeam;

    @FXML
    public TableColumn<FootballerTableView, String> positionTeam;

    @FXML
    public TableView<FootballerTableView> marketTableView;

    @FXML
    public TableColumn<FootballerTableView, ImageView> marketShieldColumn;

    @FXML
    public TableColumn<FootballerTableView, String> marketFootballerNameColumn;

    @FXML
    public TableColumn<FootballerTableView, String> marketPositionColumn;

    @FXML
    public TableColumn<FootballerTableView, FootballerTableView> marketActionColumn;

    @FXML
    public Label moneyAvailable;

    @FXML
    private ChoiceBox<String> formationChoiceBox;

    @Autowired
    MarketOfferService marketOfferService;


    @Autowired
    PlayerLeagueService playerLeagueService;

    List<PlayerLeague> playerLeagueList;

    PlayerLeague playerLeagueLogged;
    @Autowired
    LeagueService leagueService;

    League league;

    private Long leagueId;

    private Player playerLogged;

    @FXML
    public Button goalKeeper;
    @FXML
    public Button defense1;
    @FXML
    public Button defense2;
    @FXML
    public Button defense3;
    @FXML
    public Button defense4;
    @FXML
    public Button mid1;
    @FXML
    public Button mid2;
    @FXML
    public Button mid3;
    @FXML
    public Button mid4;
    @FXML
    public Button striker1;
    @FXML
    public Button striker2;

    @FXML
    private void initialize() {
    }
    @Transactional
    public void initData(Long leagueId, Player playerLogged, TableView<FootballerTableView> playerTableView, TableView<FootballerTableView> marketTableView, TableColumn<FootballerTableView, String> marketFootballerNameColumn, TableColumn<FootballerTableView, String> nameColumn, TableColumn<FootballerTableView, String> pointsColumn, TableColumn<FootballerTableView, ImageView> marketShieldColumn, TableColumn<FootballerTableView, FootballerTableView> marketActionColumn, TableView<FootballerTableView> myTeamTableView, TableColumn<FootballerTableView, ImageView> ShieldColumnTeam, TableColumn<FootballerTableView, String> marketPositionColumn, TableColumn<FootballerTableView, String> teamName, TableColumn<FootballerTableView, String> footballerNameTeam, TableColumn<FootballerTableView, String> positionTeam, Label money, Button goalKeeper, Button defense1, Button defense2, Button defense3, Button defense4, Button mid1, Button mid2, Button mid3, Button mid4, Button striker1, Button striker2) {
        this.leagueId = leagueId;
        this.playerLogged = playerLogged;
        this.marketTableView = marketTableView;
        this.marketFootballerNameColumn = marketFootballerNameColumn;
        this.playerTableView = playerTableView;
        this.nameColumn = nameColumn;
        this.pointsColumn = pointsColumn;
        this.marketShieldColumn = marketShieldColumn;
        this.marketActionColumn = marketActionColumn;
        this.myTeamTableView = myTeamTableView;
        this.ShieldColumnTeam = ShieldColumnTeam;
        this.marketPositionColumn = marketPositionColumn;
        this.teamName = teamName;
        this.footballerNameTeam = footballerNameTeam;
        this.positionTeam = positionTeam;
        this.moneyAvailable = money;
        this.goalKeeper = goalKeeper;
        this.defense1 = defense1;
        this.defense2 = defense2;
        this.defense3 = defense3;
        this.defense4 = defense4;
        this.mid1 = mid1;
        this.mid2 = mid2;
        this.mid3 = mid3;
        this.mid4 = mid4;
        this.striker1 = striker1;
        this.striker2 = striker2;
        loadPlayers();
        loadMyTeam();
        loadMarket();
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
        ShieldColumnTeam.setCellValueFactory(new PropertyValueFactory<>("photo"));
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


        assignPosition(footballers, "PORTERO", List.of(goalKeeper), 1);
        assignPosition(footballers, "DEFENSA", List.of(defense1, defense2, defense3, defense4), 4);
        assignPosition(footballers, "CENTROCAMPISTA", List.of(mid1, mid2, mid3, mid4), 4);
        assignPosition(footballers, "DELANTERO", List.of(striker1, striker2), 2);


        hideHeaders(myTeamTableView);
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

    private void loadMarket() {
        league = leagueService.findLeagueById(leagueId);

        ObservableList<FootballerTableView> footballerTableViewList = FXCollections.observableArrayList();

        league.getFootballersMarket().forEach(f -> {
            ImageView imageView = new ImageView(f.getTeam().getPhoto());
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            footballerTableViewList.add(new FootballerTableView(imageView, f.getName(), f.getRol(), f));
        });
        marketShieldColumn.setCellValueFactory(new PropertyValueFactory<>("photo"));
        marketFootballerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        marketPositionColumn.setCellValueFactory(new  PropertyValueFactory<>("rol"));
        marketActionColumn.setCellFactory(param -> new TableCell<FootballerTableView, FootballerTableView>() {
            private final Button button = new Button("Pujar");
            {
                button.setOnAction(event -> {

                    FootballerTableView currentItem = getTableView().getItems().get(getIndex());
                    if (currentItem != null) {
                        Footballer footballer = currentItem.getFootballer();
                        boolean hasOffer = marketOfferService.playerHasActiveOffer(footballer, playerLogged.getId(), leagueId);

                        if (!hasOffer){
                            try {
                                FantasyApplication.showMarketBuyScene(league.getFootballersMarket().get(getIndex()), playerLeagueLogged, leagueId, playerLogged.getId(), MenuController.this);
                            } catch (IOException e) {
                                throw new RuntimeException(e);}
                        } else {
                            try {
                                FantasyApplication.showMarketBuyUpdateScene(league.getFootballersMarket().get(getIndex()), playerLeagueLogged, leagueId, playerLogged.getId(), MenuController.this);
                            } catch (IOException e) {
                                throw new RuntimeException(e);}
                        }
                    }

                });
            }
            @Override
            protected void updateItem(FootballerTableView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {

                    /*Footballer footballer = item.getFootballer();
                    if (footballer == null) {
                        System.err.println("Futbolista no encontrado en FootballerTableView");
                        setGraphic(null);
                        return;
                    }
                    boolean hasOffer = marketOfferService.playerHasActiveOffer(footballer, playerLogged.getId(), leagueId);
                    System.out.println("Futbolista ID: " + footballer.getId() + ", Has active offer: " + hasOffer);

                    button.setText(hasOffer ? "Actualizar oferta" : "Pujar");
                    System.out.println("Button text set to: " + button.getText());*/

                    setGraphic(button);

                    /*FootballerTableView currentItem = getTableView().getItems().get(getIndex());
                    Long footballerId = currentItem.getId();

                    Footballer footballer = league.getFootballersMarket().stream()
                            .filter(f -> f.getId().equals(footballerId))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("Futbolista no encontrado"));



                    boolean hasOffer = marketOfferService.playerHasActiveOffer(footballer, playerLogged.getId(), leagueId);
                    System.out.println("Has active offer: " + hasOffer);

                    button.setText(hasOffer ? "Actualizar oferta" : "Pujar");
                    setGraphic(button);
                    System.out.println("Button text set to: " + button.getText());*/

                }
            }

        });
        marketFootballerNameColumn.getStyleClass().add("center-aligned-column");
        marketTableView.setItems(footballerTableViewList);

        hideHeaders(marketTableView);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
        String moneyFormatted = decimalFormat.format(playerLeagueLogged.getMoney());

        moneyAvailable.setText(moneyAvailable.getText() + moneyFormatted);
    }

    @FXML
    public void handleLeagues() throws IOException {
        FantasyApplication.showPlayerMenuScene(playerLogged);
    }
    @FXML
    public void handleSignOut() throws IOException {
        FantasyApplication.showLoginScene();
    }

    @FXML
    public void handleInvite() throws IOException {
        FantasyApplication.showInvitePlayerScene(leagueId);
    }


    private void hideHeaders(TableView<?> tableView) {
        tableView.skinProperty().addListener((a, b, newSkin) ->
        {
            Pane header = (Pane) tableView.lookup("TableHeaderRow");
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setMaxHeight(0);
            header.setVisible(false);
        });
    }

    @FXML
    public void handleClickFootballer(Event event) throws IOException {
        Button button = (Button) event.getSource();
        String footballer = button.getText();
        playerLeagueLogged = playerLeagueService.findByLeagueIdAndPlayerId(playerLeagueLogged.getLeague().getId(), playerLeagueLogged.getPlayer().getId());
        PlayerLeagueFootballer playerLeagueFootballer = playerLeagueLogged.getPlayerLeagueFootballers().stream().filter(plf -> plf.getFootballer().getName().equals(footballer)).toList().get(0);
        FantasyApplication.showSelectFootballerMenu(playerLeagueFootballer, playerLeagueLogged, button);
    }

    private void assignPosition(List<PlayerLeagueFootballer> footballers, String role, List<Button> buttons, int number) {
        List<String> selectedNames = footballers.stream()
                .filter(f -> f.getSelected())
                .filter(f -> f.getFootballer().getRol().equals(role))
                .map(f -> f.getFootballer().getName())
                .toList();

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(i < selectedNames.size() ? selectedNames.get(i) : "Sin asignar");
        }
    }

}