package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.model.MarketOffer;
import com.futbol.Fantasy.model.PlayerLeague;
import com.futbol.Fantasy.service.FootballerService;
import com.futbol.Fantasy.service.MarketOfferService;
import com.futbol.Fantasy.service.PlayerLeagueService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
public class MarketBuyUpdateController {

    @Autowired
    FootballerService footballerService;

    @Autowired
    MarketOfferService marketOfferService;

    @Autowired
    PlayerLeagueService playerLeagueService;

    @FXML
    private TextField buyField;

    Footballer footballer;

    PlayerLeague playerLeague;

    private Long leagueId;

    private Long playerId;

    private MenuController menuController;


    @FXML
    private void handleUpdate() throws IOException {
        try {

            int newAmount = Integer.parseInt(buyField.getText());
            if (newAmount <= 0 || newAmount > playerLeague.getMoney()) {
                showError("La cantidad introducida no es válida o supera tu saldo disponible");
                return;
            }


            MarketOffer existingOffer = footballer.getMarketOfferList().stream()
                    .filter(offer ->
                            offer.getPlayerId().equals(playerId) &&
                                    offer.getLeagueId().equals(leagueId) &&
                                    !Boolean.TRUE.equals(offer.getExecuted()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No se encontró una oferta activa para este jugador."));


            int difference = newAmount - existingOffer.getAmount();
            if (difference > playerLeague.getMoney()) {
                showError("No tienes suficiente dinero para incrementar esta oferta");
                return;
            }

            existingOffer.setAmount(newAmount);
            marketOfferService.insert(existingOffer);

            playerLeague.setMoney(playerLeague.getMoney() - difference);
            playerLeagueService.save(playerLeague);

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
            symbols.setGroupingSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
            String moneyFormatted = decimalFormat.format(playerLeague.getMoney());
            menuController.moneyAvailable.setText("Dinero disponible: " + moneyFormatted);
            menuController.playerLeagueLogged.setMoney(playerLeague.getMoney());

            showSuccess("Oferta actualizada correctamente");

            Stage stage = (Stage) this.buyField.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            showError("Por favor, introduce un número válido en el campo de compra.");
        } catch (IllegalStateException e) {
            showError(e.getMessage());
        }
    }

    public void initData(Footballer footballer, PlayerLeague playerLeague, Long leagueId, Long playerId, MenuController menuController) {
        this.footballer = footballer;
        this.leagueId = leagueId;
        this.playerId = playerId;
        this.playerLeague = playerLeague;
        this.menuController = menuController;
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

