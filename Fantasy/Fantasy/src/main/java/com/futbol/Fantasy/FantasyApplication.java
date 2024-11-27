package com.futbol.Fantasy;

import com.futbol.Fantasy.controller.*;
import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.model.Player;
import com.futbol.Fantasy.model.PlayerLeague;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

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
		menuController.initData(leagueId, playerLogged, menuController.playerTableView, menuController.marketTableView, menuController.marketFootballerNameColumn,menuController.nameColumn, menuController.pointsColumn, menuController.marketShieldColumn, menuController.marketActionColumn, menuController.myTeamTableView, menuController.ShieldColumnTeam, menuController.marketPositionColumn, menuController.teamName, menuController.footballerNameTeam, menuController.positionTeam, menuController.moneyAvailable);

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
}
