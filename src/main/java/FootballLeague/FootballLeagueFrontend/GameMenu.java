package FootballLeague.FootballLeagueFrontend;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameMenu extends Stage {

    Button newGameButton;
    Button loadGameButton;
    Button settingsButton;
    Button quitButton;

    //Main menu scene
    Scene mainMenuScene;

    //New Game scene
    TextField newGameName;
    Button backButton;
    Button createGameButton;
    Scene newGameScreenScene;


    //Used to store the name of the save game
    String saveGameName;

    public GameMenu(){
        saveGameName = "";
        newGameButton = new Button();
        loadGameButton = new Button();
        settingsButton = new Button();
        quitButton = new Button();

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

        mainMenuScene = new Scene(borderPaneMainMenu, 280, 200);
        mainMenuScene.getStylesheets().add("src/main/java/FootballLeague/Stylesheets/NotTwitter.css");

        /////////////////////////////
        //     NewGame Screen      //
        /////////////////////////////

        newGameName = new TextField();
        backButton = new Button();
        createGameButton = new Button();

        backButton.setText("Back");
        createGameButton.setText("Create game");

        HBox newGameMenuButtons = new HBox();
        newGameMenuButtons.getChildren().addAll(backButton, createGameButton);
        newGameMenuButtons.setSpacing(10);

        VBox newGameMenu = new VBox();
        newGameMenu.getChildren().addAll(newGameName, newGameMenuButtons);
        newGameMenu.setSpacing(10);
        newGameMenu.setPadding(new Insets(25));

        BorderPane borderPaneNewGameScreen = new BorderPane();
        borderPaneNewGameScreen.setCenter(newGameMenu);

        newGameScreenScene = new Scene(borderPaneNewGameScreen, 280, 200);
        newGameScreenScene.getStylesheets().add("src/main/java/FootballLeague/Stylesheets/NotTwitter.css");

        /////////////////////////////
        //    Initialize Stage     //
        /////////////////////////////

        this.setTitle("Main menu");
        this.setScene(mainMenuScene);
        this.setMaxHeight(230);
        this.setMaxWidth(260);
        this.setResizable(false);
        this.show();
    }
}
