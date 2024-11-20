package com.futbol.Fantasy.controller;


import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.Player;
import com.futbol.Fantasy.service.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private PlayerService playerService;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Player player = playerService.getPlayer(username, password);
            if (player != null) {
                if ("admin".equals(player.getUserName())) {
                    FantasyApplication.showAdminMenuScene();
                } else {
                    FantasyApplication.showPlayerMenuScene(player);;
                }
            } else {
                if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                    showError("Debe ingresar un nombre de usuario y una contraseña.");
                }else {
                    showError("Usuario o contraseña incorrectos.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showError("No se ha podido realizar el inicio de sesión. Vuelva a intentarlo.");
        }
    }

    @FXML
    private void openRegister() {
        try {
            FantasyApplication.showRegisterScene();
        } catch (IOException e) {
            e.printStackTrace();
            showError("No se ha podido abrir la pantalla de registro. Vuelva a intentarlo.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}