package FootballLeague.FootballLeagueFrontend;

import FootballLeague.FootballLeagueBackend.FileHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import FootballLeague.FootballLeagueBackend.StartingXI;
import FootballLeague.FootballLeagueBackend.Tactic;
import FootballLeague.FootballLeagueBackend.GameState;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import static FootballLeague.FootballLeagueBackend.StartingXI.writeStartingXI;
import static FootballLeague.FootballLeagueBackend.Tactic.writeTactic;

//The main idea for this layout is that a border pane is used to allow us to add separate layouts to each section.
//The top section will be where the main navigation is displayed, the left panel for the secondary menu and the
//center for the main page content. These layouts are added to the different panels using the action listeners on the menu buttons

public class MainGame extends Application {

    Stage window;
    Scene scene;
    TopMenu topMenu;
    //LeftMenus
    TeamMenu teamMenu;
    LeagueMenu leagueMenu;
    TacticMenu tacticMenu;
    ScoutingMenu scoutingMenu;
    TrainingMenu trainingMenu;
    ClubMenu clubMenu;
    OptionsMenu optionsMenu;
    //Content
    OptionsPreferencesContent optionsPreferencesContent;
    LeagueTableContent leagueTableContent;
    FirstTeamContent firstTeamContent;
    TacticContent tacticContent;
    ScoutingContent scoutingContent;
    //Used to store the name of the save game
    //TODO Figure out how to get this saveGameName to the DatabaseConnection class
    String saveGameName;
    GameMenu gameMenu;



//    public static void main(String[] args) {
//        launch(args);
//    }

    public void start(Stage primaryStage) {

        //TODO only open the MainGame once the main menu has been closed (Either game created or game loaded)
        //Trying to get MainMenu integrated into the project
//        Stage stage = new Stage();
//        initUI(stage);

        gameMenu = new GameMenu();

        window = primaryStage;
        window.setTitle("Football League");
        //Replaces the default closing to instead close the program using our method
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        //////////////////////////////////////////////
        //                  TOP MENU                //
        //////////////////////////////////////////////
        //Creates the menu at the top of the window
        topMenu = new TopMenu();

        //////////////////////////////////////////
        //               LEFT MENUS             //
        //////////////////////////////////////////

        //Creates the left menu *TEAM*
        teamMenu = new TeamMenu();

        //Creates the left menu *LEAGUE*
        leagueMenu = new LeagueMenu();

        //Creates the left menu *TACTIC*
        tacticMenu = new TacticMenu();

        //Creates the left menu *SCOUTING*
        scoutingMenu = new ScoutingMenu();

        //Creates the left menu *TRAINING*
        trainingMenu = new TrainingMenu();

        //Creates the left menu *CLUB*
        clubMenu = new ClubMenu();

        //Creates the left menu *OPTIONS*
        optionsMenu = new OptionsMenu();

        //////////////////////////////////////
        //             Content              //
        //////////////////////////////////////

        //Creates the content for the *OPTIONS->PREFERENCES*
        optionsPreferencesContent = new OptionsPreferencesContent();

        //Creates the content for *LEAGUE->LEAGUE TABLE*
        leagueTableContent = new LeagueTableContent();

        //Creates the content for *TEAM->FIRST TEAM*
        firstTeamContent = new FirstTeamContent();

        //Creates the content for *TACTIC*

        tacticContent = new TacticContent();
        saveTacticButtonListener();
        resetTacticButtonListener();

        scoutingContent = new ScoutingContent();

        //TODO load tactic button... pop out? then fill all of the data

        //creates the main layout and adds the topMenu main layout and the leftMenuHome as default
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(clubMenu);

        //////////////////////////////////////
        //      Button action listeners     //
        //////////////////////////////////////

        //Depending on the button press it will set the left menu
        topMenu.teamButton.setOnAction(e -> {
            borderPane.setLeft(teamMenu);
            borderPane.setCenter(firstTeamContent);
        });
        topMenu.leagueButton.setOnAction(e -> {
            borderPane.setLeft(leagueMenu);
            borderPane.setCenter(leagueTableContent);
        });
        topMenu.tacticButton.setOnAction(e -> {
            borderPane.setLeft(tacticMenu);
            borderPane.setCenter(tacticContent);
        });
        topMenu.advanceButton.setOnAction(e -> {
            advanceGame();
        });
        topMenu.scoutingButton.setOnAction(e -> {
            borderPane.setLeft(scoutingMenu);
            borderPane.setCenter(scoutingContent);
        });
        topMenu.trainingButton.setOnAction(e -> {
            borderPane.setLeft(trainingMenu);
        });
        topMenu.clubButton.setOnAction((e -> {
            borderPane.setLeft(clubMenu);
        }));
        topMenu.optionsButton.setOnAction((e -> {
            borderPane.setLeft(optionsMenu);
            borderPane.setCenter(optionsPreferencesContent);
        }));

        optionsMenu.quitButton.setOnAction(e -> {
            closeProgram();
        });


        //Adds action listener for the resolution combo box
        optionsPreferencesContent.resolutionCB.setOnAction(e -> setResolution(optionsPreferencesContent.resolutionCB.getValue()));
        optionsPreferencesContent.themeCB.setOnAction(e -> setTheme(optionsPreferencesContent.themeCB.getValue()));

        //Creates the scene with the borderPane layout window size
        scene = new Scene(borderPane, 1020, 500);
        scene.getStylesheets().add("java/FootballLeague/FootballLeagueFrontend/Stylesheets/NotTwitter.css");
        window.setScene(scene);
        window.show();

        /////////////////////////////
        //   Home Screen Actions   //
        /////////////////////////////

        gameMenu.newGameButton.setOnAction((ActionEvent event) -> {
            pressedNewGameButton(gameMenu, gameMenu.newGameScreenScene);
        });
        gameMenu.loadGameButton.setOnAction((ActionEvent event) -> {
            pressedLoadGameButton();
        });
        gameMenu.settingsButton.setOnAction((ActionEvent event) -> {
            pressedSettingsButton();
        });
        gameMenu.quitButton.setOnAction((ActionEvent event) -> {
            pressedQuitButton();
        });

        /////////////////////////////
        // New Game Screen Actions //
        /////////////////////////////

        gameMenu.backButton.setOnAction((ActionEvent event) -> {
            pressedBackButton(gameMenu, gameMenu.mainMenuScene);
        });
        gameMenu.createGameButton.setOnAction((ActionEvent event) -> {
            pressedCreateGameButton(gameMenu.newGameName.getText());
        });


    }

    public void closeProgram(){
        Boolean answer = ConfirmBox.display("Quit?", "Are you sure you want to close the game?");
        if(answer)
            window.close();
    }

    public void advanceGame(){
        //TODO simulate all the games for this game week
        //TODO update label for game week
        //TODO update league table
        //TODO show the user's team result (With goal scorers?)
        //TODO Show all of the football results

        //Used to update the game week and year
        try {
            if(Integer.parseInt(GameState.readGameWeek("Everton1")) >= 52){
                GameState.updateGameWeek("Everton1", "1");
                GameState.nextGameYear("Everton1");
            }
            else{
                GameState.nextGameWeek("Everton1");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public void setResolution(String res){
        switch(res){
            case "800 x 400":
                window.setWidth(800);
                window.setHeight(400);
                break;
            case "1020, 500":
                window.setWidth(1020);
                window.setHeight(500);
                break;
            case "1920 x 1080":
                window.setWidth(1920);
                window.setHeight(1080);
                break;
            case "2560 x 1440":
                window.setWidth(2560);
                window.setHeight(1440);
                break;
        }
        System.out.println("Changed the resolution to " + res);
    }

    public void setTheme(String selectedTheme){
        switch(selectedTheme){
            case "Not Twitter":
                scene.getStylesheets().clear();
                scene.getStylesheets().add("main/java/FootballLeague/FootballLeagueFrontend/Stylesheets/NotTwitter.css");
                break;
            case "Dark Theme":
                scene.getStylesheets().clear();
                scene.getStylesheets().add("C:\\Users\\Georg\\Documents\\Java Projects\\FootballLeague\\src\\main\\java\\FootballLeague\\Stylesheets\\DarkTheme.css");
                break;
            case "Very Colourful":
                scene.getStylesheets().clear();
                scene.getStylesheets().add("FootballLeague/src/main/java/FootballLeague/FootballLeagueFrontend/Stylesheets/VeryColourful.css");
                break;
        }
        System.out.println("Changed the theme to " + selectedTheme);
    }

    //Add the action listeners for the reset tactic button
    public void resetTacticButtonListener(){
        //Action listener for the reset tactic button
        tacticMenu.newTacticButton.setOnAction(e -> {

            //Resets the text of all of the labels on the right hand side of the screen to *
            if(tacticContent.getChildren().contains(tacticContent.p1)){
                tacticContent.p1.setText("*");
                tacticContent.p2.setText("*");
                tacticContent.p3.setText("*");
                tacticContent.p4.setText("*");
                tacticContent.p5.setText("*");
                tacticContent.p6.setText("*");
                tacticContent.p7.setText("*");
                tacticContent.p8.setText("*");
                tacticContent.p9.setText("*");
                tacticContent.p10.setText("*");
                tacticContent.p11.setText("*");
            }

            //Resets the value of the combo boxes to null
            if(tacticContent.getChildren().contains(tacticContent.positionOneCB)){
                //Resets the values of all of the combo boxes to null
                tacticContent.positionOneCB.setValue(null);
                tacticContent.positionTwoCB.setValue(null);
                tacticContent.positionThreeCB.setValue(null);
                tacticContent.positionFourCB.setValue(null);
                tacticContent.positionFiveCB.setValue(null);
                tacticContent.positionSixCB.setValue(null);
                tacticContent.positionSevenCB.setValue(null);
                tacticContent.positionEightCB.setValue(null);
                tacticContent.positionNineCB.setValue(null);
                tacticContent.positionTenCB.setValue(null);
                tacticContent.positionElevenCB.setValue(null);
            }

            //Removes everything from the screen
            tacticContent.clearScreen();
            //Adds all of the necessary nodes to the screen
            tacticContent.add(tacticContent.formationLabel, 0, 1);
            tacticContent.add(tacticContent.formation, 1, 1);
            tacticContent.add(tacticContent.playStyleLabel, 2, 1);
            tacticContent.add(tacticContent.playStyle, 3, 1);
        });
    }

    //Adds the action listeners for the save tactic button
    public void saveTacticButtonListener(){
        //Save tactic button
        tacticMenu.saveTacticButton.setOnAction(e -> {
            //Check that each position contains a player
            if(tacticContent.positionOneCB.getValue() == null || tacticContent.positionTwoCB.getValue() == null || tacticContent.positionThreeCB.getValue() == null || tacticContent.positionFourCB.getValue() == null || tacticContent.positionFiveCB.getValue() == null || tacticContent.positionSixCB.getValue() == null || tacticContent.positionSevenCB.getValue() == null || tacticContent.positionEightCB.getValue() == null || tacticContent.positionNineCB.getValue() == null || tacticContent.positionTenCB.getValue() == null || tacticContent.positionElevenCB.getValue() == null){
                //Team not filled out
                AlertBox.display("Incomplete Team", "You must add players to each position before saving");
            } else {
                //Write the tactic to the database
                System.out.println(tacticContent.positionTwoCB.getValue().getPlayerCode());
                StartingXI startingXI = new StartingXI(tacticContent.positionOneCB.getValue().getPlayerCode(), tacticContent.positionTwoCB.getValue().getPlayerCode(), tacticContent.positionThreeCB.getValue().getPlayerCode(), tacticContent.positionFourCB.getValue().getPlayerCode(), tacticContent.positionFiveCB.getValue().getPlayerCode(), tacticContent.positionSixCB.getValue().getPlayerCode(), tacticContent.positionSevenCB.getValue().getPlayerCode(), tacticContent.positionEightCB.getValue().getPlayerCode(), tacticContent.positionNineCB.getValue().getPlayerCode(), tacticContent.positionTenCB.getValue().getPlayerCode(), tacticContent.positionElevenCB.getValue().getPlayerCode());
                writeStartingXI(startingXI);


                //Reads what formation has been selected
                String selectedFormation = tacticContent.formation.getValue();
                //TODO just filled with dummy variables for now
                Tactic tactic = new Tactic(startingXI.getStartingXICode(),0.5,0.6,selectedFormation,tacticContent.playStyle.getValue());
                //Write the tactic to the database
                writeTactic(tactic);
                //Pop out to say tactic saved
                AlertBox.display("Tactic Saved", "Your tactic " + tactic.getTacticCode() + " has been saved!");
            }
        });
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                createNewGame(saveGameName, false);
            } catch (IOException e) {
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

    private void createNewGame(String saveGameName, Boolean overwrite) throws IOException {
        FileHandler f = new FileHandler();
        String mainGameName = "mainGame";
        try {
            f.copyBaseSaveGame(mainGameName, saveGameName, overwrite);
        } catch(IOException e) {
            e.printStackTrace();
        }
        this.saveGameName = saveGameName;
        //This will create the JSON file to keep track of the game state such as game week and team
        //TODO add a way to select the team from the main menu
        GameState.initGameState("007SPU", saveGameName);

        //Used to store the save name
        GameState.writeSaveName(saveGameName);
    }
}
