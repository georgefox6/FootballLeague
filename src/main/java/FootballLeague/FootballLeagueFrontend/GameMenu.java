package FootballLeague.FootballLeagueFrontend;

import java.io.IOException;
import java.util.ArrayList;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Team;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import FootballLeague.FootballLeagueBackend.FileHandler;

public class GameMenu extends Application {

    ComboBox<Team> teamSelector;

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
        mainMenuScene.getStylesheets().add("FootballLeague/FootballLeagueFrontend/Stylesheets/NotTwitter.css");

        /////////////////////////////
        //     NewGame Screen      //
        /////////////////////////////

        TextField newGameName = new TextField();
        Button backButton = new Button();
        Button createGameButton = new Button();
        ObservableList<Team> teamList = getAllTeams();
        teamSelector = new ComboBox<>(teamList);

        backButton.setText("Back");
        createGameButton.setText("Create game");

        HBox newGameMenuButtons = new HBox();
        newGameMenuButtons.getChildren().addAll(backButton, createGameButton);
        newGameMenuButtons.setSpacing(10);

        VBox newGameMenu = new VBox();
//        newGameMenu.getChildren().addAll(newGameName, newGameMenuButtons);
        newGameMenu.getChildren().addAll(newGameName, teamSelector, newGameMenuButtons);
        newGameMenu.setSpacing(10);
        newGameMenu.setPadding(new Insets(25));

        BorderPane borderPaneNewGameScreen = new BorderPane();
        borderPaneNewGameScreen.setCenter(newGameMenu);

        Scene newGameScreenScene = new Scene(borderPaneNewGameScreen, 280, 200);
        newGameScreenScene.getStylesheets().add("FootballLeague/FootballLeagueFrontend/Stylesheets/NotTwitter.css");


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
        stage.setMaxHeight(230);
        stage.setMaxWidth(260);
        stage.setResizable(false);
        stage.show();
    }
    private void pressedNewGameButton(Stage stage, Scene newGameScreenScene) {
        stage.setTitle("New game");
        stage.setScene(newGameScreenScene);
    }

    private void pressedLoadGameButton() {
        //TODO get list of all save games, allow user to pick save game then set the save name to the selected one
    }

    private void pressedSettingsButton() {

    }

    private void pressedQuitButton() {
        // stage.close();
    }

    private void pressedCreateGameButton(String saveGameName) {
        System.out.println("Create game");
        if (checkGameExists(saveGameName)) {
            Boolean answer = ConfirmBox.display("Game already exists:", "Are you sure you want to overwrite this game?");
            if (answer) {
                try {
                    createNewGame(saveGameName, true);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                createNewGame(saveGameName, false);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void pressedBackButton(Stage stage, Scene mainMenuScene) {
        System.out.println("Back");
        stage.setTitle("Main menu");
        stage.setScene(mainMenuScene);
    }

    private boolean checkGameExists(String saveGameName) {
        FileHandler f = new FileHandler();
        ArrayList<String> saveGameNames = f.getSaveGameNames();
        boolean gameExists = saveGameNames.contains(saveGameName);
        return gameExists;
    }

    private void createNewGame(String saveGameName, Boolean overwrite) throws IOException, InterruptedException {
        FileHandler f = new FileHandler();
        String mainGameName = "mainGame";
        try {
            f.copyBaseSaveGame(mainGameName, saveGameName, overwrite);
        } catch(IOException e) {
            e.printStackTrace();
        }
        //This will create the JSON file to keep track of the game state such as game week and team
        //TODO add a way to select the team from the main menu
        GameState.initGameState(teamSelector.getValue().getTeamCode(), saveGameName);

        //Used to store the save name
        GameState.writeSaveName(saveGameName);

        //Once the save has been created, an instance of the main game will be created
        new MainGame();
    }

    public ObservableList<Team> getAllTeams(){
        ArrayList<Team> teams = Team.readAllTeamsMain("");
        return FXCollections.observableArrayList(teams);
    }

    public static void main(String[] args) {
        launch(args);
    }
}