package FootballLeagueFrontend;

import FootballLeagueBackend.Player;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

import static FootballLeagueBackend.Database.readPlayersTeam;

//The main idea for this layout is that a border pane is used to allow us to add separate layouts to each section.
//The top section will be where the main navigation is displayed, the left panel for the secondary menu and the
//center for the main page content. These layouts are added to the different panels using the action listeners on the menu buttons

public class MainGame extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){
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
        HBox topMenu = new HBox();
        Button teamButton = new Button("Team");
        Button leagueButton = new Button("League");
        Button tacticButton = new Button("Tactics");
        Button advanceButton = new Button("Advance");
        Button scoutingButton = new Button("Scouting");
        Button trainingButton = new Button("Training");
        Button clubButton = new Button("Club");
        Button optionsButton = new Button("Options");
        Label gameWeek = new Label("Game Week 41");
        //Adds the buttons to the menu
        topMenu.getChildren().addAll(teamButton, leagueButton, tacticButton,  scoutingButton, gameWeek, trainingButton, clubButton, optionsButton, advanceButton);
        //Sets the spacing for the top Menu
        topMenu.setSpacing(10);
        topMenu.setPadding(new Insets(15, 12, 15, 12));

        //////////////////////////////////////////
        //               LEFT MENUS             //
        //////////////////////////////////////////

        //Creates the left menu *TEAM*
        VBox leftMenuTeam = new VBox();
        Button firstTeamButton = new Button("First Team");
        Button youthTeamButton = new Button("Youth Team");
        Button womenTeamButton = new Button("Women's Team");
        //Adds the content to the left menu team
        leftMenuTeam.getChildren().addAll(firstTeamButton, youthTeamButton, womenTeamButton);
        leftMenuTeam.setSpacing(10);
        leftMenuTeam.setPadding(new Insets(15, 12, 15, 12));

        //Creates the left menu *LEAGUE*
        VBox leftMenuLeague = new VBox();
        Button leagueTableButton = new Button("League Table");
        Button topScorersButton = new Button("Top Scorers");
        Button resultsButton = new Button("Results");
        Button fixturesButton = new Button("Fixtures");
        //Adds the content to the left menu League
        leftMenuLeague.getChildren().addAll(leagueTableButton, topScorersButton, resultsButton, fixturesButton);
        leftMenuLeague.setSpacing(10);
        leftMenuLeague.setPadding(new Insets(15, 12, 15, 12));

        //Creates the left menu *TACTIC*
        VBox leftMenuTactic = new VBox();
        Button newTacticButton = new Button("New Tactic");
        Button loadTacticButton = new Button("Load Tactic");
        Button saveTacticButton = new Button("Save Tactic");
        //Adds the content to the left menu Tactic
        leftMenuTactic.getChildren().addAll(newTacticButton, loadTacticButton, saveTacticButton);
        leftMenuTactic.setSpacing(10);
        leftMenuTactic.setPadding(new Insets(15, 12, 15, 12));

        //Creates the left menu *TRAINING*
        VBox leftMenuTraining = new VBox();
        //TODO change this button to something that makes sense
        Button abcButton = new Button("ABC");
        //Adds the content to the left menu Training
        leftMenuTraining.getChildren().addAll(abcButton);
        leftMenuTraining.setSpacing(10);
        leftMenuTraining.setPadding(new Insets(15, 12, 15, 12));

        //Creates the left menu *SCOUTING*
        VBox leftMenuScouting = new VBox();
        Button viewPlayersButton = new Button("View Players");
        Button shortlistButton = new Button("Shortlist");
        Button scoutsButton = new Button("Scouts");
        //Adds the content to the left menu Scouting
        leftMenuScouting.getChildren().addAll(viewPlayersButton, shortlistButton, scoutsButton);
        leftMenuScouting.setSpacing(10);
        leftMenuScouting.setPadding(new Insets(15, 12, 15, 12));

        //Creates the left menu *CLUB*
        VBox leftMenuClub = new VBox();
        Button facilitiesButton = new Button("Facilities");
        Button historyButton = new Button("History");
        Button staffButton = new Button("Staff");
        Button financesButton = new Button("Finances");
        //Adds the content to the left menu Club
        leftMenuClub.getChildren().addAll(facilitiesButton, historyButton, staffButton, financesButton);
        leftMenuClub.setSpacing(10);
        leftMenuClub.setPadding(new Insets(15, 12, 15, 12));

        //Creates the left menu *OPTIONS*
        VBox leftMenuOptions = new VBox();
        Button optionsOptionsButton = new Button("Options");
        Button saveGameOptionsButton = new Button("Save Game");
        Button loadGameOptionsButton = new Button("Load Game");
        Button quitButton = new Button("Quit Game");
        //Adds the content to the left menu Options
        leftMenuOptions.getChildren().addAll(optionsOptionsButton, saveGameOptionsButton, loadGameOptionsButton, quitButton);
        leftMenuOptions.setSpacing(10);
        leftMenuOptions.setPadding(new Insets(15, 12, 15, 12));

        //////////////////////////////////////
        //             Content              //
        //////////////////////////////////////

        //Creates the content for the *OPTIONS->PREFERENCES*
        GridPane optionsPreferencesContent = new GridPane();
        optionsPreferencesContent.setHgap(10);
        optionsPreferencesContent.setVgap(10);
        optionsPreferencesContent.setPadding(new Insets(0, 10, 0, 10));
        //Adds the resolution option label
        Label resolutionLabel = new Label("Resolution:");
        optionsPreferencesContent.add(resolutionLabel, 2, 1);
        //Creates a comboBox for the resolution options
        ComboBox resolutionCB = new ComboBox();
        resolutionCB.getItems().addAll("800 x 400", "1020, 500", "1920 x 1080", "2560 x 1440");
        optionsPreferencesContent.add(resolutionCB, 3, 1);
        //Adds the action listener to perform function setResolution when value is chosen from the ComboBox(No need to confirm with button)
        resolutionCB.setOnAction(e -> setResolution(resolutionCB.getValue().toString()));
        //Adds the theme options
        Label themeLabel = new Label("Theme:");
        optionsPreferencesContent.add(themeLabel, 2, 2);
        //Creates the comboBox to pick the theme
        ComboBox themeCB = new ComboBox();
        themeCB.getItems().addAll("Not Twitter", "Dark Theme", "Very Colourful");
        optionsPreferencesContent.add(themeCB, 3, 2);
        //Adds the action listener to perform function setTheme when value is chosen from the ComboBox(No need to confirm with button)
        themeCB.setOnAction(e -> setTheme(themeCB.getValue().toString()));

        //Creates the content for *TEAM->FIRST TEAM*
        //Creates the forename Column
        TableColumn<Player, String> forenameColumn = new TableColumn<>("Forename");
        forenameColumn.setMinWidth(200);
        forenameColumn.setCellValueFactory(new PropertyValueFactory<>("forename"));
        //Creates the surname Column
        TableColumn<Player, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setMinWidth(200);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        //Creates the injury Column
        TableColumn<Player, Boolean> injuryColumn = new TableColumn<>("Injury Status");
        injuryColumn.setMinWidth(200);
        injuryColumn.setCellValueFactory(new PropertyValueFactory<>("injuryStatus"));
        //Creates the table view
        TableView firstTeamTable = new TableView();
        //Populates the table with players from the users team
        firstTeamTable.setItems(getPlayersFromTeam());
        //Adds all of the columns to the table
        firstTeamTable.getColumns().addAll(forenameColumn, surnameColumn, injuryColumn);



        //creates the main layout and adds the topMenu main layout and the leftMenuHome as default
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenuClub);

        //////////////////////////////////////
        //      Button action listeners     //
        //////////////////////////////////////

        //Depending on the button press it will set the left menu
        teamButton.setOnAction(e -> {
            borderPane.setLeft(leftMenuTeam);
            borderPane.setCenter(firstTeamTable);
        });
        leagueButton.setOnAction(e -> {
            borderPane.setLeft(leftMenuLeague);
        });
        tacticButton.setOnAction(e -> {
            borderPane.setLeft(leftMenuTactic);
        });
        advanceButton.setOnAction(e -> {
            //TODO add advance game function
        });
        scoutingButton.setOnAction(e -> {
            borderPane.setLeft(leftMenuScouting);
        });
        trainingButton.setOnAction(e -> {
            borderPane.setLeft(leftMenuTraining);
        });
        clubButton.setOnAction((e -> {
            borderPane.setLeft(leftMenuClub);
        }));
        optionsButton.setOnAction((e -> {
            borderPane.setLeft(leftMenuOptions);
            borderPane.setCenter(optionsPreferencesContent);
        }));
        quitButton.setOnAction(e -> {
            closeProgram();
        });

        //Creates the scene with the borderPane layout window size
        Scene scene = new Scene(borderPane, 1020, 500);
        scene.getStylesheets().add("FootballLeagueFrontend/FootballLeagueThemeOne.css");
        window.setScene(scene);
        window.show();
    }

    public void closeProgram(){
        Boolean answer = ConfirmBox.display("Quit?", "Are you sure you want to close the game?");
        if(answer)
            window.close();
    }

    public void setResolution(String res){
        //TODO Write function to change resolution
        System.out.println("Changed the resolution to " + res);
    }

    public void setTheme(String selectedTheme){
        //TODO Write function to change the theme (change the CSS)
        System.out.println("Changed the theme to " + selectedTheme);
    }

    public ObservableList<Player> getPlayersFromTeam(){
        //TODO This needs changing to the users team code
        ArrayList<Player> players = readPlayersTeam("006SPU");
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        return playerList;
    }
}
