package FootballLeague.FootballLeagueFrontend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import FootballLeague.FootballLeagueBackend.*;
import FootballLeague.FootballLeagueBackend.WebScraper.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.imageio.ImageIO;

import static FootballLeague.FootballLeagueFrontend.MainGame.initNewGame;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GameMenu extends Application {

    public static Logger logger = LogManager.getLogger("com.josh");

    ComboBox<Team> teamSelector;
    ComboBox<String> baseGameSelector;
    ProgressBox progressBox;
    CreateDbVbox createDbVbox;
    Button createGameButton;
    Stage stage;

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
        mainMenuScene.getStylesheets().clear();
        mainMenuScene.getStylesheets().add("stylesheets/DarkTheme.css");


        /////////////////////////////
        //     NewGame Screen      //
        /////////////////////////////

        baseGameSelector = new ComboBox<>();

        TextField newGameName = new TextField();
        newGameName.setDisable(true);

        Button backButton = new Button();

        createGameButton = new Button();
        createGameButton.setDisable(true);

        teamSelector = new ComboBox<>();
        teamSelector.setDisable(true);


        backButton.setText("Back");
        createGameButton.setText("Create game");

        HBox newGameMenuButtons = new HBox();
        newGameMenuButtons.getChildren().addAll(backButton, createGameButton);
        newGameMenuButtons.setSpacing(10);

        VBox newGameMenu = new VBox();
        newGameMenu.getChildren().addAll(baseGameSelector, newGameName, teamSelector, newGameMenuButtons);
        newGameMenu.setSpacing(10);
        newGameMenu.setPadding(new Insets(25));

        BorderPane borderPaneNewGameScreen = new BorderPane();
        borderPaneNewGameScreen.setCenter(newGameMenu);

        Scene newGameScreenScene = new Scene(borderPaneNewGameScreen, 280, 200);
        newGameScreenScene.getStylesheets().add("/src/main/Stylesheets/NotTwitter.css");

        /////////////////////////////
        //    LoadGame Screen      //
        /////////////////////////////

        ObservableList<String> saveList = getAllSaves();
        ComboBox<String> saveGameSelector = new ComboBox<>(saveList);

        Button loadBackButton = new Button();
        loadBackButton.setText("Back");

        Button loadLoadButton = new Button();
        loadLoadButton.setText("Load Game");

        HBox loadGameMenuButtons = new HBox();
        loadGameMenuButtons.getChildren().addAll(loadBackButton, loadLoadButton);
        loadGameMenuButtons.setSpacing(10);

        VBox loadGameMenu = new VBox();
        loadGameMenu.getChildren().addAll(saveGameSelector, loadGameMenuButtons);
        loadGameMenu.setSpacing(10);
        loadGameMenu.setPadding(new Insets(25));

        BorderPane borderPaneLoadGameScreen = new BorderPane();
        borderPaneLoadGameScreen.setCenter(loadGameMenu);

        Scene loadGameScreenScene = new Scene(borderPaneLoadGameScreen, 280, 200);
        loadGameScreenScene.getStylesheets().add("stylesheets/DarkTheme.css");


        ////////////////////////////
        //    Settings Screen     //
        ////////////////////////////

        Button settingsCreateNewDBButton = new Button();
        settingsCreateNewDBButton.setText("Create DB");

        Button settingsBackButton = new Button();
        settingsBackButton.setText("Back");

        VBox settingsMenu = new VBox();
        settingsMenu.getChildren().addAll(settingsCreateNewDBButton, settingsBackButton);
        settingsMenu.setSpacing(10);
        settingsMenu.setPadding(new Insets(25));

        BorderPane borderPaneSettingsScreen = new BorderPane();
        borderPaneSettingsScreen.setCenter(settingsMenu);

        Scene settingsScene = new Scene(borderPaneSettingsScreen, 280, 200);

        /////////////////////////////
        //   Home Screen Actions   //
        /////////////////////////////

        newGameButton.setOnAction((ActionEvent event) -> {
            ObservableList<String> baseGameList = getAllBaseGames();
            baseGameSelector.setItems(baseGameList);
            pressedNewGameButton(stage, newGameScreenScene);
        });
        loadGameButton.setOnAction((ActionEvent event) -> {
            pressedLoadGameButton(stage, loadGameScreenScene);
        });
        settingsButton.setOnAction((ActionEvent event) -> {
            pressedSettingsButton(stage, settingsScene);
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
            pressedCreateGameButton(baseGameSelector.getValue(), newGameName.getText());
        });

        baseGameSelector.setOnAction(e -> {
            newGameName.setDisable(false);
            createGameButton.setDisable(false);
            teamSelector.setDisable(false);

            try {
                GameState.writeSaveName("../BaseGames/" + baseGameSelector.getValue());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            ObservableList<Team> teamList = getAllTeams();
            teamSelector.setItems(teamList);
        });

        //////////////////////////////
        // Load Game Screen Actions //
        //////////////////////////////

        loadBackButton.setOnAction((ActionEvent event) -> {
            pressedBackButton(stage, mainMenuScene);
        });

        loadLoadButton.setOnAction((ActionEvent event) -> {
            String saveName = saveGameSelector.getValue();
            loadGame(saveName);
        });

        ///////////////////////////////
        //  Settings Screen Actions  //
        ///////////////////////////////

        settingsBackButton.setOnAction(e -> {
            pressedBackButton(stage, mainMenuScene);
        });

        createDbVbox = new CreateDbVbox();
        settingsCreateNewDBButton.setOnAction(e -> {
            BorderPane borderPaneNewDB = new BorderPane();
            borderPaneNewDB.setCenter(createDbVbox);
            Scene scene = new Scene(borderPaneNewDB, 280, 200);
            stage.setScene(scene);
        });


        ///////////////////////////////
        //   Create new DB Actions   //
        ///////////////////////////////

        createDbVbox.generateButton.setOnAction(e -> {
            System.out.println(((RadioButton)createDbVbox.group.getSelectedToggle()).getText());
            String dbSize = ((RadioButton)createDbVbox.group.getSelectedToggle()).getText().split("-")[0];
            progressBox = new ProgressBox("Generating New " + dbSize + "Database");

            //Used to add the date time to the file name to make them unique
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
            String formattedDate = myDateObj.format(myFormatObj);
            String jsonFileName = dbSize.trim() + formattedDate;

            Task task = WebToJson.returnTaskJsonCreation(dbSize, jsonFileName);

            //Used to bind the progress bar to the progress of the task
            progressBox.progressBar.progressProperty().bind(task.progressProperty());

            //Code to be run once the task has been completed successfully
            task.setOnSucceeded(f -> {
                progressBox.vbox.getChildren().add(new Label("Database Complete!"));

                Button okButton = new Button("Ok");
                progressBox.vbox.getChildren().add(okButton);

                okButton.setOnAction(g -> {
                    System.out.println("close");
                    progressBox.close();
                });

                String baseGameName = dbSize.trim() + "-" + formattedDate;

                try {
                    Path source = Paths.get("src/main/resources/blank.db");
                    Path target = Paths.get("src/main/resources/BaseGames/" + baseGameName + ".db");
                    Files.copy(source, target, REPLACE_EXISTING);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }



                DatabasePopulator.jsonToDB(jsonFileName + ".json", baseGameName);

            });

            Thread thread = new Thread(task);
            thread.start();


        });

        createDbVbox.backButton.setOnAction(e -> {
            pressedBackButton(stage, settingsScene);
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

    private void pressedLoadGameButton(Stage stage, Scene loadGameScreenScene) {
        stage.setTitle("Load Game");
        stage.setScene(loadGameScreenScene);
    }

    private void pressedSettingsButton(Stage stage, Scene scene) {
        stage.setTitle("Settings");
        stage.setScene(scene);
    }

    private void pressedQuitButton() {
        // stage.close();
    }

    private void pressedCreateGameButton(String baseGame, String saveGameName) {
        logger.info("Create game");
        if (checkGameExists(saveGameName)) {
            Boolean answer = ConfirmBox.display("Game already exists:", "Are you sure you want to overwrite this game?");
            if (answer) {
                try {
                    createNewGame(baseGame, saveGameName, true);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                createNewGame(baseGame, saveGameName, false);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void pressedBackButton(Stage stage, Scene mainMenuScene) {
        logger.info("Back");
        stage.setTitle("Main menu");
        stage.setScene(mainMenuScene);
    }

    private boolean checkGameExists(String saveGameName) {
        FileHandler f = new FileHandler();
        ArrayList<String> saveGameNames = f.getSaveGameNames();
        boolean gameExists = saveGameNames.contains(saveGameName);
        return gameExists;
    }

    private void createNewGame(String baseGame, String saveGameName, Boolean overwrite) throws IOException, InterruptedException {
        FileHandler f = new FileHandler();
        try {
            f.copyBaseSaveGame(baseGame, saveGameName, overwrite);
        } catch(IOException e) {
            e.printStackTrace();
        }
        //This will create the JSON file to keep track of the game state such as game week and team
        GameState.initGameState(teamSelector.getValue().getTeamCode(), saveGameName);

        //Used to store the save name
        GameState.writeSaveName(saveGameName);

        //Initialises the new game: sets up the league tables and schedules all matches for the year
        initNewGame();

        //Once the save has been created, an instance of the main game will be created
        new MainGame();
    }

    public ObservableList<Team> getAllTeams(){
        ArrayList<Team> teams = Team.readAllTeams("");
        return FXCollections.observableArrayList(teams);
    }

    public ObservableList<String> getAllSaves(){
        FileHandler f = new FileHandler();
        return FXCollections.observableArrayList(f.getSaveGameNamesDB());
    }

    public ObservableList<String> getAllBaseGames(){
        FileHandler handler = new FileHandler();
        return FXCollections.observableArrayList(handler.getBaseGames());
    }

    private void loadGame(String saveGameName){
        try {
            GameState.writeSaveName(saveGameName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new MainGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}