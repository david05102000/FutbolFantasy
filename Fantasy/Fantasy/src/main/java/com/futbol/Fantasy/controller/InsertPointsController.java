package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.model.PlayerLeague;
import com.futbol.Fantasy.model.PlayerLeagueFootballer;
import com.futbol.Fantasy.service.PlayerLeagueFootballerService;
import com.futbol.Fantasy.service.PlayerLeagueService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class InsertPointsController {

    Long footballerId;

    @Autowired
    PlayerLeagueFootballerService playerLeagueFootballerService;

    @Autowired
    PlayerLeagueService playerLeagueService;

    @FXML
    private TextField pointsField;

    private static final int MONEY_PER_POINT = 100000;

    @FXML
    private void handleAdd() throws IOException {
        try {
            String input = pointsField.getText();
            if (!input.matches("\\d+")) {
                throw new NumberFormatException("No es un carácter válido.");
            }

            List<PlayerLeagueFootballer> playerLeagueFootballerList = playerLeagueFootballerService.findByFootballerId(footballerId);

            for (PlayerLeagueFootballer playerLeagueFootballer : playerLeagueFootballerList) {
                if (playerLeagueFootballer.getSelected()) {
                    PlayerLeague playerLeague = playerLeagueFootballer.getPlayerLeague();
                    playerLeague.setPoints(playerLeague.getPoints() + Integer.parseInt(pointsField.getText()));

                    int moneyEarned = Integer.parseInt(pointsField.getText()) * MONEY_PER_POINT;
                    playerLeague.setMoney(playerLeague.getMoney() + moneyEarned);

                    playerLeagueService.save(playerLeague);
                }
            }
            showSuccess("Se han introducido correctamente los puntos.");
            Stage stage = (Stage) this.pointsField.getScene().getWindow();

            stage.close();
        } catch (NumberFormatException e) {
            showError("Por favor introduce un número valido.");
        } catch (Exception e) {
            showError("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }

    public void initData(Long footballerId) {
        this.footballerId = footballerId;
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
        alert.setTitle("Exito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
