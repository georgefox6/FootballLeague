package FootballLeagueFrontend;

import FootballLeagueBackend.*;
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
        tacticMenu.newTacticButton.setOnAction(e -> {
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
        tacticMenu.saveTacticButton.setOnAction(e -> {
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
        borderPane.setLeft(clubMenu);

        //////////////////////////////////////
        //      Button action listeners     //
        //////////////////////////////////////

        //Depending on the button press it will set the left menu
        topMenu.teamButton.setOnAction(e -> {
            borderPane.setLeft(teamMenu);
            borderPane.setCenter(firstTeamTable);
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
            //TODO add advance game function
        });
        topMenu.scoutingButton.setOnAction(e -> {
            borderPane.setLeft(scoutingMenu);
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



    public ObservableList<Player> getPlayersFromTeam(){
        //TODO This needs changing to the users team code
        ArrayList<Player> players = readPlayersTeam("006SPU");
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
   //     playerList.add(null);
        return playerList;
    }


}
