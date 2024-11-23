package com.futbol.Fantasy;

import com.futbol.Fantasy.controller.CreateLeagueController;
import com.futbol.Fantasy.controller.MenuController;
import com.futbol.Fantasy.controller.PlayerMenuController;
import com.futbol.Fantasy.model.Player;
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
		menuController.initData(leagueId, playerLogged, menuController.playerTableView, menuController.nameColumn, menuController.pointsColumn, menuController.teamShieldColumn, menuController.actionColumn, menuController.myTeamTableView, menuController.teamShieldColumnTeam, menuController.teamName, menuController.footballerNameTeam, menuController.positionTeam, menuController.moneyAvailable);

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
}
