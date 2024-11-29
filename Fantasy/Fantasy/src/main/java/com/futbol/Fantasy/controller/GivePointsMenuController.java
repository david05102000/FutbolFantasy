package com.futbol.Fantasy.controller;

import com.futbol.Fantasy.FantasyApplication;
import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.service.FootballerService;
import com.futbol.Fantasy.table.model.FootballerTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GivePointsMenuController {

    @FXML
    public TableView<FootballerTableView> footballersTableView;

    @FXML
    public TableColumn<FootballerTableView, String> teamNameColumn;

    @FXML
    public TableColumn<FootballerTableView, String> footballerNameColumn;

    @FXML
    public TableColumn<FootballerTableView, FootballerTableView> actionColumn;

    @FXML
    private TextField searchField;

    private ObservableList<FootballerTableView> footballerTableViewList;

    @Autowired
    FootballerService footballerService;

    public void initData(TableView<FootballerTableView> footballersTableView, TableColumn<FootballerTableView, String> teamNameColumn, TableColumn<FootballerTableView, String> footballerNameColumn, TableColumn<FootballerTableView, FootballerTableView> actionColumn) {
        this.footballersTableView = footballersTableView;
        this.teamNameColumn = teamNameColumn;
        this.footballerNameColumn = footballerNameColumn;
        this.actionColumn = actionColumn;

        loadPlayers();
        setupSearchFilter();
    }

    private void loadPlayers() {
        List<Footballer> footballerList = footballerService.findAll();
        footballerTableViewList = FXCollections.observableArrayList();

        footballerList.forEach(f -> {
            footballerTableViewList.add(new FootballerTableView(f.getId(), f.getName(), f.getTeam().getName()));
        });
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        footballerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        actionColumn.setCellFactory(param -> new TableCell<FootballerTableView, FootballerTableView>() {
            private final Button button = new Button("Añadir puntos");

            {
                button.setOnAction(event -> {
                    try {
                        FantasyApplication.showInsertPoints(footballerList.get(getIndex()).getId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }


            @Override
            protected void updateItem(FootballerTableView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        footballerNameColumn.getStyleClass().add("center-aligned-column");
        footballersTableView.setItems(footballerTableViewList);


    }
    private void setupSearchFilter() {
        FilteredList<FootballerTableView> filteredList = new FilteredList<>(footballerTableViewList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(footballer -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return footballer.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<FootballerTableView> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(footballersTableView.comparatorProperty());
        footballersTableView.setItems(sortedList);
    }
}

