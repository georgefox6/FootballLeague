package FootballLeagueFrontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

public class HomeScreen extends Application {

	 @Override
	 public void start(Stage stage) {

	 	initUI(stage);
	 }


	 private void initUI(Stage stage) {

	 	Button newGameButton = new Button();
	 	Button loadGameButton = new Button();
	 	Button settingsButton = new Button();
	 	Button quitButton = new Button();
	 	
	 	newGameButton.setText("New game");
	 	loadGameButton.setText("Load game");
	 	settingsButton.setText("Settings");
	 	quitButton.setText("Quit");


	 	newGameButton.setOnAction((ActionEvent event) -> {
	 		pressedNewGameButton();
	 	});
	 	newGameButton.setOnAction((ActionEvent event) -> {
	 		pressedLoadGameButton();
	 	});
	 	newGameButton.setOnAction((ActionEvent event) -> {
	 		pressedSettingsButton();
	 	});
	 	quitButton.setOnAction((ActionEvent event) -> {
	 		pressedQuitButton();
	 	});

	 	VBox menu = new VBox();
	 	menu.getChildren().addAll(newGameButton, loadGameButton, settingsButton, quitButton);
	 	menu.setSpacing(10);
	 	menu.setPadding(new Insets(25));

	 	BorderPane borderPane = new BorderPane();
	 	borderPane.setCenter(menu);

	 	Scene scene = new Scene(borderPane, 280, 200);
		scene.getStylesheets().add("FootballLeagueFrontend/FootballLeagueThemeOne.css");

	 	stage.setTitle("Main menu");
	 	stage.setScene(scene);
	 	stage.show();
	 }

	 private void pressedNewGameButton() {

	 }

	 private void pressedLoadGameButton() {
	 	
	 }

	 private void pressedSettingsButton() {
	 	
	 }

	 private void pressedQuitButton() {
	 	Platform.exit();
	 }

	 public static void main(String[] args) {
	 	launch(args);
	 }
}