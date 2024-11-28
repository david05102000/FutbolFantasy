package com.futbol.Fantasy.controller;


import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.model.PlayerLeague;
import com.futbol.Fantasy.model.PlayerLeagueFootballer;
import com.futbol.Fantasy.service.PlayerLeagueFootballerService;
import com.futbol.Fantasy.table.model.FootballerSelectableListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Component
public class SelectFootballerMenuController {

    @Autowired
    PlayerLeagueFootballerService service;

    PlayerLeagueFootballer playerLeagueFootballer;

    Button button;

    @FXML
    public ListView<FootballerSelectableListView> selectFootballerListView;

    public void initData(PlayerLeagueFootballer playerLeagueFootballer, PlayerLeague playerLeague, ListView<FootballerSelectableListView> selectFootballerListView, Button button) {
        this.selectFootballerListView = selectFootballerListView;
        this.playerLeagueFootballer = playerLeagueFootballer;
        this.button = button;

        Footballer footballerSelected = playerLeagueFootballer.getFootballer();
        ObservableList<FootballerSelectableListView> selectables = FXCollections.observableArrayList();
        for(PlayerLeagueFootballer playerLeagueFootballerIt :playerLeague.getPlayerLeagueFootballers()) {
            if(!playerLeagueFootballerIt.getSelected() && playerLeagueFootballerIt.getFootballer().getRol().equals(footballerSelected.getRol()))  {
                selectables.add(new FootballerSelectableListView(playerLeagueFootballerIt.getPlayerLeagueFootballerId(), playerLeagueFootballerIt.getFootballer().getName(), playerLeagueFootballerIt.getFootballer().getTeam().getName()));
            }
        }
        selectFootballerListView.setItems(selectables);

    }

    @FXML
    public void handleMouseClick(MouseEvent arg0) throws IOException {
        System.out.println("clicked on " + selectFootballerListView.getSelectionModel().getSelectedItem());

        playerLeagueFootballer.setSelected(false);
        service.save(playerLeagueFootballer);

        Optional<PlayerLeagueFootballer> footballerSelectableListViewSelected =service.findById(selectFootballerListView.getSelectionModel().getSelectedItem().getId());
        if(footballerSelectableListViewSelected.isPresent()) {
            footballerSelectableListViewSelected.get().setSelected(true);
            service.save(footballerSelectableListViewSelected.get());
            button.setText(footballerSelectableListViewSelected.get().getFootballer().getName());
        }

        Stage stage = (Stage) this.selectFootballerListView.getScene().getWindow();

        stage.close();
    }
}
