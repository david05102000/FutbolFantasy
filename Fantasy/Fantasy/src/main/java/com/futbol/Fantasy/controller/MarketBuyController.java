package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.model.MarketOffer;
import com.futbol.Fantasy.model.PlayerLeague;
import com.futbol.Fantasy.service.FootballerService;
import com.futbol.Fantasy.service.MarketOfferService;
import com.futbol.Fantasy.service.PlayerLeagueService;
import com.futbol.Fantasy.table.model.FootballerTableView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
public class MarketBuyController {


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
    private void handleBuy() throws IOException {
        try {

            if(Integer.parseInt(buyField.getText()) > 0 && Integer.parseInt(buyField.getText()) <= playerLeague.getMoney()) {
                MarketOffer marketOffer = new MarketOffer();
                marketOffer.setAmount(Integer.parseInt(buyField.getText()));
                marketOffer.setLeagueId(leagueId);
                marketOffer.setPlayerId(playerId);
                marketOffer.setExecuted(false);

                marketOffer = marketOfferService.insert(marketOffer);

                footballer.getMarketOfferList().add(marketOffer);

                footballerService.insert(footballer);

                playerLeague.setMoney(playerLeague.getMoney() - Integer.parseInt(buyField.getText()));
                playerLeagueService.save(playerLeague);

                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
                symbols.setGroupingSeparator('.');
                DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
                String moneyFormatted = decimalFormat.format(playerLeague.getMoney());
                menuController.moneyAvailable.setText("Dinero disponible: " + moneyFormatted);
                menuController.playerLeagueLogged.setMoney(playerLeague.getMoney());

                showSuccess("Oferta enviada");

                Stage stage = (Stage) this.buyField.getScene().getWindow();
                stage.close();
            }
            else {
            showError("La cantidad introducida no es válida o supera tu saldo disponible");
        }
        } catch (NumberFormatException e) {
            showError("Por favor, introduce un número válido en el campo de compra.");
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
        alert.setTitle("Exito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
