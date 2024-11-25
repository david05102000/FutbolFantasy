package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.League;
import com.futbol.Fantasy.model.Player;
import com.futbol.Fantasy.service.LeagueService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class PlayerMenuController {
    @FXML
    private ListView<String> playerLeaguesListView;

    @Autowired
    LeagueService leagueService;

    Player playerLogged;

    @FXML
    public void initialize() {
    }

    private void loadLeagues() {
        ObservableList<String> leagues = FXCollections.observableArrayList();
        try {
            leagues.addAll(leagueService.findAllLeaguesByPlayerAndStatus(playerLogged.getId(), "playing").stream()
                    .map(League::getLeagueNameCode)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            showError("Error al cargar las ligas.");
        }

        if (leagues.isEmpty()) {
            leagues.add("Todavía no estás en ninguna liga.");
        }
        playerLeaguesListView.setItems(leagues);
    }

    @FXML
    public void handleMouseClick(MouseEvent arg0) throws IOException {
        FantasyApplication.showMenuScene(Long.valueOf(playerLeaguesListView.getSelectionModel().getSelectedItem().split("#")[1]), playerLogged);
        System.out.println("clicked on " + playerLeaguesListView.getSelectionModel().getSelectedItem());
    }


    @FXML
    public void handleLogOut() throws IOException {
        FantasyApplication.showLoginScene();
    }

    @FXML
    public void handleCreateLeague() throws IOException {
        FantasyApplication.showCreateLeagueScene(playerLogged);
    }

    @FXML
    public void handleInvitations() throws IOException {
        FantasyApplication.showInvitationMenu(playerLogged.getId());
    }

    public void initData(Player loggedUser) {
        playerLogged = loggedUser;
        loadLeagues();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Registro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro Exitoso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
