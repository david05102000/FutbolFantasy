package com.futbol.Fantasy.controller;


import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.Player;
import com.futbol.Fantasy.service.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class RegisterController {
    @Autowired
    PlayerService playerService;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;


    public RegisterController() {
    }

    @FXML
    private void handleRegister() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!validateInput(username, password)) {
            return;
        }

        if (playerService.existsByUsername(username)) {
            showError("El nombre de usuario ya está en uso. Por favor, elige otro nombre de usuario.");
            return;
        }

        try {
            Player player = new Player();
            player.setUserName(username);
            player.setPassword(password);

            playerService.save(player);
            showSuccess("Usuario " + username + " registrado con exito.");
            if ("admin".equals(player.getUserName())) {
                FantasyApplication.showAdminMenuScene();
            } else {
                FantasyApplication.showPlayerMenuScene(player);
            }
        } catch (Exception e) {
            showError("Error al registrar el usuario: " + e.getMessage());
        }
    }
    @FXML
    private void handleCancel() throws IOException {
        FantasyApplication.showLoginScene();
    }

    private boolean validateInput(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showError("El nombre de usuario y la contraseña no pueden estar vacíos.");
            return false;
        }
        return true;
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
