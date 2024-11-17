package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.utils.WebScraper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class AdminMenuController {

    @Autowired
    private WebScraper webScraper;

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
    public void handleLogOut() throws IOException {
        FantasyApplication.showLoginScene();
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

