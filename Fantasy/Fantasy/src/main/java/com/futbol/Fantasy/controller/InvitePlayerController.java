package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.model.League;
import com.futbol.Fantasy.model.Player;
import com.futbol.Fantasy.service.LeagueService;
import com.futbol.Fantasy.service.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class InvitePlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    LeagueService leagueService;

    @FXML
    private TextField usernameField;

    private Long leagueId;

    @FXML
    private void handleInvite() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            showError("El campo de nombre de usuario no puede estar vacío.");
            return;
        }
        try {
            Optional<Player> player = playerService.findByUserName(username);

            if (player.isPresent()) {
                League league = new League();
                if (leagueService.isPlayerInLeague(leagueId, player.get().getId())) {
                    showError("El usuario " + username + " ya está registrado en la liga.");
                }
                else {
                    league = leagueService.findLeagueById(leagueId);
                    leagueService.saveLeagueWithPlayer(league, player.get(), "invited");
                    showSuccess("Usuario " + username + " invitado.");
                    Stage stage = (Stage) this.usernameField.getScene().getWindow();

                    stage.close();
                }
            } else {
                showError("Usuario con el nombre: " + username + " no encontrado");
            }
        } catch (Exception e) {
            showError("No se ha podido invitar al usuario: " + e.getMessage());
        }
    }

    public void initData(Long leagueId) {
        this.leagueId = leagueId;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}