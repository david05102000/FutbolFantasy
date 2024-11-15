package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.utils.WebScraper;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminMenuController {

    @Autowired
    private WebScraper webScraper;

    @FXML
    public void handleInsertTeamsAndFootballers() {
        try {
            webScraper.getTeamsAndFootballersAndSave();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

