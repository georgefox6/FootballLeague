package FootballLeagueFrontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.ActionEvent;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import FootballLeagueBackend.FileHandler;

public class NewGameScreenFX extends Application {

	public NewGameScreenFX() {

		initUI();
	}

	// TODO
	// This method may not be necessary but may be best practice so should be included.
	// @Override
	// public void start(Stage stage) {

	// 	initUI(stage);
	// }

	private void initUI(Stage stage) {

		Button newGameButton = new Button();
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
		close();
		//new HomeScreen().setVisible(true);
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