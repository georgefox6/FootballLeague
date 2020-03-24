package FootballLeague.FootballLeagueFrontend;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import FootballLeague.FootballLeagueBackend.Match;
import FootballLeague.FootballLeagueFrontend.Content.*;
import FootballLeague.FootballLeagueFrontend.InnerMenu.*;
import FootballLeague.FootballLeagueBackend.StartingXI;
import FootballLeague.FootballLeagueBackend.Tactic;
import FootballLeague.FootballLeagueBackend.GameState;
import org.json.simple.parser.ParseException;

import static FootballLeague.FootballLeagueBackend.Match.readAllMatches;
import static FootballLeague.FootballLeagueBackend.StartingXI.writeStartingXI;
import static FootballLeague.FootballLeagueBackend.Tactic.writeTactic;

//The main idea for this layout is that a border pane is used to allow us to add separate layouts to each section.
//The top section will be where the main navigation is displayed, the left panel for the secondary menu and the
//center for the main page content. These layouts are added to the different panels using the action listeners on the menu buttons

public class MainGame extends Stage {

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
    AdvanceFixturesContent advanceFixturesContent;
    AdvanceResultsContent advanceResultsContent;

    //Constructor for the main game stage
    public MainGame(){
        //Schedule all matches for the league
        Match.scheduleMatches();

        this.setTitle("Football League");

        //Replaces the default closing to instead close the program using our method
        this.setOnCloseRequest(e -> {
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

        //Creates the content for *ADVANCE->FIXTURES*
        advanceFixturesContent = new AdvanceFixturesContent();

        //Creates the content for *ADVANCE->RESULTS*
        advanceResultsContent = new AdvanceResultsContent();

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
            borderPane.setCenter(advanceFixturesContent);
            this.advanceFixturesContent.update();
            //TODO the left pane needs clearing
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

        advanceFixturesContent.nextButton.setOnAction(e -> {
            advanceGame();
            borderPane.setCenter(advanceResultsContent);
            advanceResultsContent.update();
            advanceGameWeek();
        });

        advanceResultsContent.doneButton.setOnAction(e -> {
            borderPane.setCenter(leagueTableContent);
        });

        //TODO make the done button on advanceResultsContent do something (Maybe return you to home / show league table)


        //Adds action listener for the resolution combo box
        optionsPreferencesContent.resolutionCB.setOnAction(e -> setResolution(optionsPreferencesContent.resolutionCB.getValue()));
        optionsPreferencesContent.themeCB.setOnAction(e -> setTheme(optionsPreferencesContent.themeCB.getValue()));

        //Creates the scene with the borderPane layout window size
        scene = new Scene(borderPane, 1020, 500);
        scene.getStylesheets().add("java/FootballLeague/FootballLeagueFrontend/Stylesheets/NotTwitter.css");
        this.setScene(scene);
        this.show();


    }

    public void closeProgram(){
        Boolean answer = ConfirmBox.display("Quit?", "Are you sure you want to close the game?");
        if(answer)
            this.close();
    }

    public void advanceGame(){
        //Creates the clause to search for matches
        String clause = "";
        try {
            clause = "WHERE date='" + GameState.readGameWeek(GameState.readSaveName()) + "'";
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        //Used to simulate all of the matches for that week
        ArrayList<Match> matchesThisWeek = readAllMatches(clause);

        for(Match match : matchesThisWeek){
            //TODO match simulations don't currently work - nothing been written to the score in match table of DB
            match.playMatch();
        }
    }

    public void advanceGameWeek(){
        //Used to update the game week and year
        try {
            if(Integer.parseInt(GameState.readGameWeek(GameState.readSaveName())) >= 52){
                GameState.updateGameWeek(GameState.readSaveName(), "1");
                GameState.nextGameYear(GameState.readSaveName());
            }
            else{
                GameState.nextGameWeek(GameState.readSaveName());
            }
            topMenu.gameWeek.setText("Game Week " + GameState.readGameWeek(GameState.readSaveName()));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void setResolution(String res){
        switch(res){
            case "800 x 400":
                this.setWidth(800);
                this.setHeight(400);
                break;
            case "1020, 500":
                this.setWidth(1020);
                this.setHeight(500);
                break;
            case "1920 x 1080":
                this.setWidth(1920);
                this.setHeight(1080);
                break;
            case "2560 x 1440":
                this.setWidth(2560);
                this.setHeight(1440);
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
}
