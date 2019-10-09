package FootballLeagueFrontend;

import FootballLeagueBackend.Database;
import FootballLeagueBackend.Player;
import FootballLeagueBackend.StartingXI;
import FootballLeagueBackend.Tactic;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
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
        Button newTacticButton = new Button("Reset Tactic");
        Button loadTacticButton = new Button("Load Tactic");
        Button saveTacticButton = new Button("Save Tactic");
        //Adds the content to the left menu Tactic
        leftMenuTactic.getChildren().addAll(newTacticButton, saveTacticButton, loadTacticButton);
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

        //Creates the content for *TACTIC*
        GridPane tacticContent = new GridPane();
        tacticContent.setHgap(10);
        tacticContent.setVgap(10);
        tacticContent.setPadding(new Insets(0, 10, 0, 10));
        //Sets the min column width to 50px
        for (int i = 0; i < 10; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setMinWidth(50);
            tacticContent.getColumnConstraints().add(column);
        }
        //Creates the formation selector ComboBox and adds it to the screen
        ComboBox<String> formation = new ComboBox();
        formation.getItems().addAll("4-4-2", "4-3-3", "5-3-2", "3-4-3");
        tacticContent.add(formation, 2, 1);
        Label formationLabel = new Label("Formation:");
        tacticContent.add(formationLabel, 1, 1);
        //Adds the play style selector comboBox and adds it to the screen
        ComboBox<String> playStyle = new ComboBox();
        playStyle.getItems().addAll("Tiki Taka", "Gegenpress", "Counter Attack", "Park the bus");
        tacticContent.add(playStyle, 4, 1);
        Label playStyleLabel = new Label("Play style:");
        tacticContent.add(playStyleLabel, 3, 1);
        //Creates labels for every possible position which will be added to the screen depending on the chosen formation
        Label gkLabel = new Label("GK:");
        Label rbLabel = new Label("RB:");
        Label rcbLabel = new Label("RCB:");
        Label cbLabel = new Label("CB:");
        Label lcbLabel = new Label("LCB:");
        Label lbLabel = new Label("LB:");
        Label rcmLabel = new Label("RCM:");
        Label cmLabel = new Label("CM:");
        Label lcmLabel = new Label("LCM:");
        Label rmLabel = new Label("RM:");
        Label lmLabel = new Label("LM:");
        Label rwLabel = new Label("RW:");
        Label lwLabel = new Label("LW:");
        Label rstLabel = new Label("RST:");
        Label lstLabel = new Label("LST:");
        Label stLabel = new Label("ST:");
        //Adds all of the ComboBoxes to select the players for each position
        ComboBox<Player> positionOneCB = new ComboBox();
        ComboBox<Player> positionTwoCB = new ComboBox();
        ComboBox<Player> positionThreeCB = new ComboBox();
        ComboBox<Player> positionFourCB = new ComboBox();
        ComboBox<Player> positionFiveCB = new ComboBox();
        ComboBox<Player> positionSixCB = new ComboBox();
        ComboBox<Player> positionSevenCB = new ComboBox();
        ComboBox<Player> positionEightCB = new ComboBox();
        ComboBox<Player> positionNineCB = new ComboBox();
        ComboBox<Player> positionTenCB = new ComboBox();
        ComboBox<Player> positionElevenCB = new ComboBox();
        //adds the list of players from that team to the combo box
        ObservableList<Player> playerList = getPlayersFromTeam();
        positionOneCB.setItems(playerList);
        positionTwoCB.setItems(playerList);
        positionThreeCB.setItems(playerList);
        positionFourCB.setItems(playerList);
        positionFiveCB.setItems(playerList);
        positionSixCB.setItems(playerList);
        positionSevenCB.setItems(playerList);
        positionEightCB.setItems(playerList);
        positionNineCB.setItems(playerList);
        positionTenCB.setItems(playerList);
        positionElevenCB.setItems(playerList);
        //Labels used to display the players on the right of the screen in formation
        Label p1 = new Label("*");
        Label p2 = new Label("*");
        Label p3 = new Label("*");
        Label p4 = new Label("*");
        Label p5 = new Label("*");
        Label p6 = new Label("*");
        Label p7 = new Label("*");
        Label p8 = new Label("*");
        Label p9 = new Label("*");
        Label p10 = new Label("*");
        Label p11 = new Label("*");
        //Adds an action listener to the formation combobox which will add the correct labels to the screen
        formation.setOnAction(e -> {
            //Removes all previous labels from the screen
            if(tacticContent.getChildren().contains(gkLabel)){
                tacticContent.getChildren().removeAll(gkLabel, rbLabel, rcbLabel, cbLabel, lcbLabel, lbLabel, rmLabel, rcmLabel, cmLabel, lcmLabel, lmLabel, rwLabel, lwLabel, stLabel, rstLabel, lstLabel);
            }
            //Removes all of the previous * or player names from the right of the screen
            if(tacticContent.getChildren().contains(p1)){
                tacticContent.getChildren().removeAll(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
            }
            //Adds all of the comboBoxes to the screen if they are not already there
            if(!tacticContent.getChildren().contains(positionOneCB)){
                tacticContent.addColumn(2,positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);
            }
            String form = formation.getValue();
            if(form == "4-4-2"){
                tacticContent.addColumn(1, gkLabel, rbLabel, rcbLabel, lcbLabel, lbLabel, rmLabel, rcmLabel, lcmLabel, lmLabel, rstLabel, lstLabel);
                //Sets the positions on the right of the screen according to the formation
                tacticContent.add(p1, 7, 10);
                tacticContent.add(p2, 9, 8);
                tacticContent.add(p3, 8, 8);
                tacticContent.add(p4, 6, 8);
                tacticContent.add(p5, 5, 8);
                tacticContent.add(p6, 9, 6);
                tacticContent.add(p7, 8, 6);
                tacticContent.add(p8, 6, 6);
                tacticContent.add(p9, 5, 6);
                tacticContent.add(p10, 8, 4);
                tacticContent.add(p11, 6, 4);
            }
            if(form == "4-3-3"){
                tacticContent.addColumn(1, gkLabel, rbLabel, rcbLabel, lcbLabel, lbLabel, rcmLabel, cmLabel, lcmLabel, rwLabel, stLabel, lwLabel);
                //Sets the positions on the right of the screen according to the formation
                tacticContent.add(p1, 7, 10);
                tacticContent.add(p2, 9, 8);
                tacticContent.add(p3, 8, 8);
                tacticContent.add(p4, 6, 8);
                tacticContent.add(p5, 5, 8);
                tacticContent.add(p6, 8, 6);
                tacticContent.add(p7, 7, 6);
                tacticContent.add(p8, 6, 6);
                tacticContent.add(p9, 9, 4);
                tacticContent.add(p10, 7, 4);
                tacticContent.add(p11, 5, 4);
            }
            if(form == "5-3-2"){
                tacticContent.addColumn(1, gkLabel, rbLabel, rcbLabel, cbLabel, lcbLabel, lbLabel, rcmLabel, cmLabel, lcmLabel, rstLabel, lstLabel);
                //Sets the positions on the right of the screen according to the formation
                tacticContent.add(p1, 7, 10);
                tacticContent.add(p2, 9, 8);
                tacticContent.add(p3, 8, 8);
                tacticContent.add(p4, 7, 8);
                tacticContent.add(p5, 6, 8);
                tacticContent.add(p6, 5, 8);
                tacticContent.add(p7, 8, 6);
                tacticContent.add(p8, 7, 6);
                tacticContent.add(p9, 6, 6);
                tacticContent.add(p10, 8, 4);
                tacticContent.add(p11, 6, 4);
            }
            if(form == "3-4-3"){
                tacticContent.addColumn(1, gkLabel, rcbLabel, cbLabel, lcbLabel, rmLabel, rcmLabel, lcmLabel, lmLabel, rwLabel, stLabel, lwLabel);
                //Sets the positions on the right of the screen according to the formation
                tacticContent.add(p1, 7, 10);
                tacticContent.add(p2, 8, 8);
                tacticContent.add(p3, 7, 8);
                tacticContent.add(p4, 6, 8);
                tacticContent.add(p5, 9, 6);
                tacticContent.add(p6, 8, 6);
                tacticContent.add(p7, 6, 6);
                tacticContent.add(p8, 5, 6);
                tacticContent.add(p9, 9, 4);
                tacticContent.add(p10, 7, 4);
                tacticContent.add(p11, 5, 4);
            }
        });
        //Replaces the * with player name when a player is selected in the combo box
        //Checks that the value of the combo box is not null as this will cause an error
        positionOneCB.setOnAction(e -> {
            if(positionOneCB.getValue() != null){
                p1.setText(positionOneCB.getValue().toString());
            }
        });
        positionTwoCB.setOnAction(e -> {
            if(positionTwoCB.getValue() != null){
                p2.setText(positionTwoCB.getValue().toString());
            }
        });
        positionThreeCB.setOnAction(e -> {
            if(positionThreeCB.getValue() != null){
                p3.setText(positionThreeCB.getValue().toString());
            }
        });
        positionFourCB.setOnAction(e -> {
            if(positionFourCB.getValue() != null){
                p4.setText(positionFourCB.getValue().toString());
            }
        });
        positionFiveCB.setOnAction(e -> {
            if(positionFiveCB.getValue() != null){
                p5.setText(positionFiveCB.getValue().toString());
            }
        });
        positionSixCB.setOnAction(e -> {
            if(positionSixCB.getValue() != null){
                p6.setText(positionSixCB.getValue().toString());
            }
        });
        positionSevenCB.setOnAction(e -> {
            if(positionSevenCB.getValue() != null){
                p7.setText(positionSevenCB.getValue().toString());
            }
        });
        positionEightCB.setOnAction(e -> {
            if(positionEightCB.getValue() != null){
                p8.setText(positionEightCB.getValue().toString());
            }
        });
        positionNineCB.setOnAction(e -> {
            if(positionNineCB.getValue() != null){
                p9.setText(positionNineCB.getValue().toString());
            }
        });
        positionTenCB.setOnAction(e -> {
            if(positionTenCB.getValue() != null){
                p10.setText(positionTenCB.getValue().toString());
            }
        });
        positionElevenCB.setOnAction(e -> {
            if(positionElevenCB.getValue() != null){
                p11.setText(positionElevenCB.getValue().toString());
            }
        });
        //Action listener for the reset tactic button
        newTacticButton.setOnAction(e -> {
            //Removes the labels from the right of the screen and sets the value back to *
            if(tacticContent.getChildren().contains(p1)){
                p1.setText("*");
                p2.setText("*");
                p3.setText("*");
                p4.setText("*");
                p5.setText("*");
                p6.setText("*");
                p7.setText("*");
                p8.setText("*");
                p9.setText("*");
                p10.setText("*");
                p11.setText("*");
                tacticContent.getChildren().removeAll(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
            }
            //Removes all previous labels from the screen
            if(tacticContent.getChildren().contains(gkLabel)){
                tacticContent.getChildren().removeAll(gkLabel, rbLabel, rcbLabel, cbLabel, lcbLabel, lbLabel, rmLabel, rcmLabel, cmLabel, lcmLabel, lmLabel, rwLabel, lwLabel, stLabel, rstLabel, lstLabel);
            }
            //removes the comboboxes from the screen
            if(tacticContent.getChildren().contains(positionOneCB)){
                //Resets the values of all of the comboboxes to null
                positionOneCB.setValue(null);
                positionTwoCB.setValue(null);
                positionThreeCB.setValue(null);
                positionFourCB.setValue(null);
                positionFiveCB.setValue(null);
                positionSixCB.setValue(null);
                positionSevenCB.setValue(null);
                positionEightCB.setValue(null);
                positionNineCB.setValue(null);
                positionTenCB.setValue(null);
                positionElevenCB.setValue(null);
                formation.setValue(null);
                tacticContent.getChildren().removeAll(positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);
            }
        });
        //Save tactic button
        saveTacticButton.setOnAction(e -> {
            //Check that each position contains a player
            if(positionOneCB.getValue() == null || positionTwoCB.getValue() == null || positionThreeCB.getValue() == null || positionFourCB.getValue() == null || positionFiveCB.getValue() == null || positionSixCB.getValue() == null || positionSevenCB.getValue() == null || positionEightCB.getValue() == null || positionNineCB.getValue() == null || positionTenCB.getValue() == null || positionElevenCB.getValue() == null){
                //Team not filled out
                AlertBox.display("Incomplete Team", "You must add players to each position before saving");
            } else {
                //Write the tactic to the database
                System.out.println(positionTwoCB.getValue().getPlayerCode());
                //TODO This currently just saves the goalkeeper as the 7 subs, change this to allow the user to pick subs or remove subs
                StartingXI startingXI = new StartingXI(positionOneCB.getValue().getPlayerCode(), positionTwoCB.getValue().getPlayerCode(), positionThreeCB.getValue().getPlayerCode(), positionFourCB.getValue().getPlayerCode(), positionFiveCB.getValue().getPlayerCode(), positionSixCB.getValue().getPlayerCode(), positionSevenCB.getValue().getPlayerCode(), positionEightCB.getValue().getPlayerCode(), positionNineCB.getValue().getPlayerCode(), positionTenCB.getValue().getPlayerCode(), positionElevenCB.getValue().getPlayerCode(), positionOneCB.getValue().getPlayerCode(), positionOneCB.getValue().getPlayerCode(), positionOneCB.getValue().getPlayerCode(), positionOneCB.getValue().getPlayerCode(), positionOneCB.getValue().getPlayerCode(), positionOneCB.getValue().getPlayerCode(), positionOneCB.getValue().getPlayerCode());
                Database.writestartingXI(startingXI);
                //Reads what formation has been selected
                String selectedFormation = formation.getValue();
                //TODO just filled with dummy variables for now
                Tactic tactic = new Tactic(startingXI.getStartingXICode(),0.5,0.5,selectedFormation,playStyle.getValue());
                //Write the tactic to the database
                Database.writeTactic(tactic);
                //Popout to say tactic saved
                AlertBox.display("Tactic Saved", "Your tactic " + tactic.getTacticCode() + " has been saved!");
            }
        });


        //TODO load tactic button... pop out? then fill all of the data




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
            borderPane.setCenter(tacticContent);
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
   //     playerList.add(null);
        return playerList;
    }
}
