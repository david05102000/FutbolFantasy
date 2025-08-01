package com.futbol.Fantasy;

import com.futbol.Fantasy.controller.*;
import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.model.Player;
import com.futbol.Fantasy.model.PlayerLeague;
import com.futbol.Fantasy.model.PlayerLeagueFootballer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
public class FantasyApplication extends Application {


	private static ConfigurableApplicationContext context;
	private static Scene scene;

	private static Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		context = new SpringApplicationBuilder(FantasyApplication.class).run();
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		FantasyApplication.primaryStage = primaryStage;
		showLoginScene();
	}

	public static void showLoginScene() throws IOException {
		primaryStage.setTitle("Fantasy");
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/login.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();
		scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void showAdminMenuScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/adminMenu.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();
		scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void showPlayerMenuScene(Player player) throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/playerMenu.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();
		scene = new Scene(root);

		PlayerMenuController playerMenuController = loader.getController();
		playerMenuController.initData(player);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void showCreateLeagueScene(Player player) throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/createLeague.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();

		Stage newStage = new Stage();

		Scene newScene = new Scene(root);
		newStage.setScene(newScene);

		newStage.initOwner(primaryStage);
		newStage.setOnHiding(event -> event.consume());

		CreateLeagueController createLeagueController = loader.getController();
		createLeagueController.initData(player);

		newStage.show();
	}

	public static void showMenuScene(Long leagueId, Player playerLogged) throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/menu.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();
		scene = new Scene(root);

		MenuController menuController = loader.getController();
		menuController.initData(leagueId, playerLogged, menuController.playerTableView, menuController.marketTableView, menuController.marketFootballerNameColumn,menuController.nameColumn, menuController.pointsColumn, menuController.marketShieldColumn, menuController.marketActionColumn, menuController.myTeamTableView, menuController.ShieldColumnTeam, menuController.marketPositionColumn, menuController.teamName, menuController.footballerNameTeam, menuController.positionTeam, menuController.moneyAvailable, menuController.goalKeeper, menuController.defense1, menuController.defense2, menuController.defense3, menuController.defense4, menuController.mid1, menuController.mid2, menuController.mid3, menuController.mid4, menuController.striker1, menuController.striker2);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void showRegisterScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/register.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();
		scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void showInvitePlayerScene(Long leagueId) throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/invitePlayer.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();

		Stage newStage = new Stage();
		Scene newScene = new Scene(root);
		newStage.setScene(newScene);

		newStage.initOwner(primaryStage);
		newStage.setOnHiding(event -> event.consume());

		InvitePlayerController invitePlayerController = loader.getController();
		invitePlayerController.initData(leagueId);

		newStage.show();
	}

	public static void showInvitationMenu(Long id) throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/invitationMenu.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();

		Stage newStage = new Stage();

		Scene newScene = new Scene(root);
		newStage.setScene(newScene);

		newStage.initOwner(primaryStage);
		newStage.setOnHiding(event -> event.consume());

		InvitationMenuController invitationMenuController = loader.getController();
		invitationMenuController.initData(id, invitationMenuController.tableView, invitationMenuController.leagueName, invitationMenuController.buttons);

		newStage.show();
	}

	public static void showMarketBuyScene(Footballer footballer, PlayerLeague playerLeague, Long leagueId, Long playerId, MenuController menuController) throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/marketBuy.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();

		Stage newStage = new Stage();
		Scene newScene = new Scene(root);
		newStage.setScene(newScene);

		newStage.initOwner(primaryStage);
		newStage.setOnHiding(event -> event.consume());

		MarketBuyController marketBuyController = loader.getController();
		marketBuyController.initData(footballer, playerLeague, leagueId, playerId, menuController);

		newStage.show();
	}

	public static void showMarketBuyUpdateScene(Footballer footballer, PlayerLeague playerLeague, Long leagueId, Long playerId, MenuController menuController) throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/marketUpdateBuy.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();

		Stage newStage = new Stage();
		Scene newScene = new Scene(root);
		newStage.setScene(newScene);

		newStage.initOwner(primaryStage);
		newStage.setOnHiding(event -> event.consume());

		MarketBuyUpdateController marketBuyUpdateController = loader.getController();
		marketBuyUpdateController.initData(footballer, playerLeague, leagueId, playerId, menuController);

		newStage.show();
	}

	public static void showSelectFootballerMenu(PlayerLeagueFootballer playerLeagueFootballer, PlayerLeague playerLeague, Button button) throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/selectFootballerMenu.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();

		Stage newStage = new Stage();
		Scene newScene = new Scene(root);
		newStage.setScene(newScene);

		newStage.initOwner(primaryStage);
		newStage.setOnHiding(event -> event.consume());

		SelectFootballerMenuController selectFootballerMenuController = loader.getController();
		selectFootballerMenuController.initData(playerLeagueFootballer, playerLeague, selectFootballerMenuController.selectFootballerListView, button);

		newStage.show();
	}

	public static void showGivePointsMenu() throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/givePointsMenu.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();

		Stage newStage = new Stage();
		Scene newScene = new Scene(root);
		newStage.setResizable(false);
		newStage.setScene(newScene);

		newStage.initOwner(primaryStage);
		newStage.setOnHiding(event -> event.consume());

		GivePointsMenuController givePointsMenuController = loader.getController();
		givePointsMenuController.initData(givePointsMenuController.footballersTableView, givePointsMenuController.teamNameColumn, givePointsMenuController.footballerNameColumn, givePointsMenuController.actionColumn);

		newStage.show();
	}

	public static void showInsertPoints(Long footballerId) throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/templates/insertPoints.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();

		Stage newStage = new Stage();
		Scene newScene = new Scene(root);
		newStage.setScene(newScene);

		newStage.initOwner(primaryStage);
		newStage.setOnHiding(event -> event.consume());

		InsertPointsController insertPointsController = loader.getController();
		insertPointsController.initData(footballerId);

		newStage.show();
	}

	@Override
	public void stop() {
		try {
			context.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
