package com.futbol.Fantasy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
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
		showAdminMenuScene();
	}
	public static void showAdminMenuScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(FantasyApplication.class.getResource("/views/adminMenu.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();
		scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
