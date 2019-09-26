package FootballLeagueFrontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import FootballLeagueBackend.FileHandler;

public class MainMenu extends Application {

	 @Override
	 public void start(Stage stage) {

	 	initUI(stage);
	 }

	 private void initUI(Stage stage) {

	 	/////////////////////////////
	 	//      Home Screen        //
	 	/////////////////////////////

	 	Button newGameButton = new Button();
	 	Button loadGameButton = new Button();
	 	Button settingsButton = new Button();
	 	Button quitButton = new Button();
	 	
	 	newGameButton.setText("New game");
	 	loadGameButton.setText("Load game");
	 	settingsButton.setText("Settings");
	 	quitButton.setText("Quit");

	 	VBox mainMenu = new VBox();
	 	mainMenu.getChildren().addAll(newGameButton, loadGameButton, settingsButton, quitButton);
	 	mainMenu.setSpacing(10);
	 	mainMenu.setPadding(new Insets(25));

	 	BorderPane borderPaneMainMenu = new BorderPane();
	 	borderPaneMainMenu.setCenter(mainMenu);

	 	Scene mainMenuScene = new Scene(borderPaneMainMenu, 280, 200);
		mainMenuScene.getStylesheets().add("FootballLeagueFrontend/FootballLeagueThemeOne.css");

		/////////////////////////////
	 	//     NewGame Screen      //
	 	/////////////////////////////
		
		TextField newGameName = new TextField();
		Button backButton = new Button();
		Button createGameButton = new Button();

		backButton.setText("Back");
		createGameButton.setText("Create game");

		VBox newGameMenu = new VBox();
		newGameMenu.getChildren().addAll(newGameName, backButton, createGameButton);
		newGameMenu.setSpacing(10);
		newGameMenu.setPadding(new Insets(25));

		BorderPane borderPaneNewGameScreen = new BorderPane();
	 	borderPaneNewGameScreen.setCenter(newGameMenu);

	 	Scene newGameScreenScene = new Scene(borderPaneNewGameScreen, 280, 200);
		newGameScreenScene.getStylesheets().add("FootballLeagueFrontend/FootballLeagueThemeOne.css");

		
		/////////////////////////////
		//   Home Screen Actions   //
		/////////////////////////////

		newGameButton.setOnAction((ActionEvent event) -> {
	 		pressedNewGameButton(stage, newGameScreenScene);
	 	});
	 	loadGameButton.setOnAction((ActionEvent event) -> {
	 		pressedLoadGameButton();
	 	});
	 	settingsButton.setOnAction((ActionEvent event) -> {
	 		pressedSettingsButton();
	 	});
	 	quitButton.setOnAction((ActionEvent event) -> {
	 		pressedQuitButton();
	 	});

	 	/////////////////////////////
		// New Game Screen Actions //
		/////////////////////////////

	 	backButton.setOnAction((ActionEvent event) -> {
			pressedBackButton(stage, mainMenuScene);
		});
		createGameButton.setOnAction((ActionEvent event) -> {
			pressedCreateGameButton(newGameName.getText());
		});

		/////////////////////////////
		//    Initialize Stage     //
		/////////////////////////////

	 	stage.setTitle("Main menu");
	 	stage.setScene(mainMenuScene);
	 	stage.show();
	 }

	 private void pressedNewGameButton(Stage stage, Scene newGameScreenScene) {
	 	stage.setTitle("New game");
	 	stage.setScene(newGameScreenScene);
	 }

	 private void pressedLoadGameButton() {
	 	
	 }

	 private void pressedSettingsButton() {
	 	
	 }

	 private void pressedQuitButton() {
	 	// stage.close();
	 }

	 private void pressedCreateGameButton(String saveGameName) {
		System.out.println("Create game");
		try {
			createNewGame(saveGameName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void pressedBackButton(Stage stage, Scene mainMenuScene) {
		System.out.println("Back");
		stage.setTitle("Main menu");
		stage.setScene(mainMenuScene);
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

	 public static void main(String[] args) {
	 	launch(args);
	 }
}