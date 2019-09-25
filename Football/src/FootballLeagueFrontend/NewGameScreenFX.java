package FootballLeagueFrontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import FootballLeagueBackend.FileHandler;

public class NewGameScreenFX extends Application {

	Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	// public NewGameScreenFX() {

	// 	start();
	// }

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}

	private void initUI(Stage primaryStage) {

		stage = primaryStage;

		TextField newGameName = new TextField();
		Button backButton = new Button();
		Button createGameButton = new Button();

		backButton.setText("Back");
		createGameButton.setText("Create game");

		backButton.setOnAction((ActionEvent event) -> {
			pressedBackButton();
		});
		createGameButton.setOnAction((ActionEvent event) -> {
			pressedCreateGameButton(newGameName.getText());
		});

		VBox menu = new VBox();
		menu.getChildren().addAll(newGameName, backButton, createGameButton);
		menu.setSpacing(10);
		menu.setPadding(new Insets(25));

		BorderPane borderPane = new BorderPane();
	 	borderPane.setCenter(menu);

	 	Scene scene = new Scene(borderPane, 280, 200);
		scene.getStylesheets().add("FootballLeagueFrontend/FootballLeagueThemeOne.css");

	 	stage.setTitle("New game");
	 	stage.setScene(scene);
	 	stage.show();
	}

	private void pressedCreateGameButton(String saveGameName) {
		System.out.println("Create game");
		try {
			createNewGame(saveGameName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void pressedBackButton() {
		System.out.println("Back");
		stage.close();
		HomeScreen h = new HomeScreen();
	}

	private void createNewGame(String saveGameName) throws IOException {
		FileHandler f = new FileHandler();
		String mainGameName = "mainGame";
		try {
			f.copyDatabase(mainGameName, saveGameName);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}