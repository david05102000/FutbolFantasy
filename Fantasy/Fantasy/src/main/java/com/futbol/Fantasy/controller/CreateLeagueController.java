package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.*;
import com.futbol.Fantasy.service.FootballerService;
import com.futbol.Fantasy.service.LeagueService;
import com.futbol.Fantasy.service.PlayerLeagueService;
import com.futbol.Fantasy.service.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class CreateLeagueController {

    @Autowired
    LeagueService leagueService;

    @Autowired
    FootballerService footballerService;

    @Autowired
    PlayerLeagueService playerLeagueService;

    @Autowired
    PlayerService playerService;

    @FXML
    private TextField leagueNameField;

    Player playerLogged;

    @FXML
    public void handleCreate() throws IOException {
        if(!leagueNameField.getText().isEmpty() && !leagueNameField.getText().contains("#")) {
            League league = new League();
            league.setName(leagueNameField.getText());


            Player player = playerService.findById(playerLogged.getId());

            PlayerLeague playerLeague = leagueService.saveLeagueWithPlayer(league, player, "playing");


            playerLeagueService.save(playerLeague);

            FantasyApplication.showPlayerMenuScene(playerLogged);
            Stage stage = (Stage) this.leagueNameField.getScene().getWindow();

            stage.close();
        }
    }
    public void initData(Player loggedUser) {
        playerLogged = loggedUser;
        System.out.println(loggedUser.getUserName());
    }
}