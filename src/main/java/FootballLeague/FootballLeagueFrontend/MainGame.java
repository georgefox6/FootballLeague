package FootballLeague.FootballLeagueFrontend;

import FootballLeague.FootballLeagueBackend.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

import FootballLeague.FootballLeagueFrontend.Content.*;
import FootballLeague.FootballLeagueFrontend.InnerMenu.*;
import org.json.simple.parser.ParseException;

import static FootballLeague.FootballLeagueBackend.GameState.readCurrentTeam;
import static FootballLeague.FootballLeagueBackend.LeagueTableEntry.readUniqueLeagues;
import static FootballLeague.FootballLeagueBackend.Match.readAllMatches;
import static FootballLeague.FootballLeagueBackend.Match.updateMatch;
import static FootballLeague.FootballLeagueBackend.StartingXI.readStartingXI;
import static FootballLeague.FootballLeagueBackend.StartingXI.writeStartingXI;
import static FootballLeague.FootballLeagueBackend.Tactic.*;
import static FootballLeague.FootballLeagueBackend.Team.readAllTeams;

//The main idea for this layout is that a border pane is used to allow us to add separate layouts to each section.
//The top section will be where the main navigation is displayed, the left panel for the secondary menu and the
//center for the main page content. These layouts are added to the different panels using the action listeners on the menu buttons

public class MainGame extends Stage {

    Scene scene;
    BorderPane borderPane;
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
    LeagueResultsContent leagueResultsContent;
    LeagueFixturesContent leagueFixturesContent;

    //Constructor for the main game stage
    public MainGame(){
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
        loadTacticButtonListener();
        autoPickButtonListener();

        scoutingContent = new ScoutingContent();

        leagueResultsContent= new LeagueResultsContent();

        leagueFixturesContent = new LeagueFixturesContent();

        //creates the main layout and adds the topMenu main layout and the leftMenuHome as default
        borderPane = new BorderPane();
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
            leagueTableContent.updateLeagueTable();
            borderPane.setCenter(leagueTableContent);
        });
        leagueMenu.resultsButton.setOnAction(e -> {
            leagueResultsContent.updateContent();
            borderPane.setCenter(leagueResultsContent);
        });
        leagueMenu.fixturesButton.setOnAction(e -> {
            leagueFixturesContent.updateContent();
            borderPane.setCenter(leagueFixturesContent);
        });
        topMenu.tacticButton.setOnAction(e -> {
            borderPane.setLeft(tacticMenu);
            borderPane.setCenter(tacticContent);
        });
        topMenu.advanceButton.setOnAction(e -> {
            borderPane.setCenter(advanceFixturesContent);
            advanceFixturesContent.update();
            HBox hBox = new HBox();
            hBox.setMinWidth(100);
            borderPane.setLeft(hBox);
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
        });

        advanceResultsContent.doneButton.setOnAction(e -> {
            leagueTableContent.updateLeagueTable();
            borderPane.setCenter(leagueTableContent);
            borderPane.setLeft(leagueMenu);
        });

        //Adds action listener for the resolution combo box
        optionsPreferencesContent.resolutionCB.setOnAction(e -> setResolution(optionsPreferencesContent.resolutionCB.getValue()));
        optionsPreferencesContent.themeCB.setOnAction(e -> setTheme(optionsPreferencesContent.themeCB.getValue()));

        //Creates the scene with the borderPane layout window size
        scene = new Scene(borderPane, 1000, 550);
        scene.getStylesheets().add("stylesheets/DarkTheme.css");
        this.setScene(scene);
        this.show();
    }

    public void closeProgram(){
        Boolean answer = ConfirmBox.display("Quit?", "Are you sure you want to close the game?");
        if(answer)
            this.close();
    }

    public Tactic getCurrentTactic(){
        StartingXI startingXI = new StartingXI(tacticContent.positionOneCB.getValue().getPlayerCode(),
                tacticContent.positionTwoCB.getValue().getPlayerCode(),
                tacticContent.positionThreeCB.getValue().getPlayerCode(),
                tacticContent.positionFourCB.getValue().getPlayerCode(),
                tacticContent.positionFiveCB.getValue().getPlayerCode(),
                tacticContent.positionSixCB.getValue().getPlayerCode(),
                tacticContent.positionSevenCB.getValue().getPlayerCode(),
                tacticContent.positionEightCB.getValue().getPlayerCode(),
                tacticContent.positionNineCB.getValue().getPlayerCode(),
                tacticContent.positionTenCB.getValue().getPlayerCode(),
                tacticContent.positionElevenCB.getValue().getPlayerCode()
        );
        writeStartingXI(startingXI);
        Tactic tactic = new Tactic(startingXI.getStartingXICode(), tacticContent.attackingScore, tacticContent.creativeScore, tacticContent.defensiveScore, tacticContent.formation.getValue(), tacticContent.playStyle.getValue(), "");
        writeTactic(tactic);
        return tactic;
    }

    public void advanceGame(){
        //This is used to set the tactic of the players team for the next match
        Tactic currentTactic = new Tactic();
        try {
            currentTactic = getCurrentTactic();
        } catch(Exception e){
            AlertBox.display("Incomplete Team", "You must have a complete team before advancing");
            borderPane.setCenter(tacticContent);
            borderPane.setLeft(tacticMenu);
            return;
        }


        try {
            ArrayList<Match> homeMatches = readAllMatches("WHERE homeTeamCode='" + GameState.readTeam(GameState.readSaveName()) +
                    "' and date='" + GameState.readGameWeek(GameState.readSaveName()) + "'");

            ArrayList<Match> awayMatches = readAllMatches("WHERE awayTeamCode='" + GameState.readTeam(GameState.readSaveName()) +
                    "' and date='" + GameState.readGameWeek(GameState.readSaveName()) + "'");

            if(homeMatches.size() != 0){
                Match hm = homeMatches.get(0);
                hm.setHomeTacticCode(currentTactic.getTacticCode());
                updateMatch(hm);
            } else if(awayMatches.size() != 0) {
                Match am = awayMatches.get(0);
                am.setAwayTacticCode(currentTactic.getTacticCode());
                updateMatch(am);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        //Creates the clause to search for matches
        String clause = "";
        clause = "WHERE date='" + GameState.readGameWeek(GameState.readSaveName()) + "'";

        //Used to simulate all of the matches for that week
        ArrayList<Match> matchesThisWeek = readAllMatches(clause);

        for(Match match : matchesThisWeek){
            match.playMatch();
        }
        borderPane.setCenter(advanceResultsContent);
        advanceResultsContent.update();
        advanceGameWeek();
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
                scene.getStylesheets().add("stylesheets/NotTwitter.css");
                break;
            case "Dark Theme":
                scene.getStylesheets().clear();
                scene.getStylesheets().add("stylesheets/DarkTheme.css");
                break;
            case "Very Colourful":
                scene.getStylesheets().clear();
                scene.getStylesheets().add("stylesheets/VeryColourful.css");
                break;
        }
        System.out.println("Changed the theme to " + selectedTheme);
    }

    //Add the action listeners for the reset tactic button
    public void resetTacticButtonListener(){
        //Action listener for the reset tactic button
        tacticMenu.newTacticButton.setOnAction(e -> {
            clearTactic();
            tacticContent.formation.valueProperty().set(null);
            tacticContent.playStyle.valueProperty().set(null);
            //Removes everything from the screen
            tacticContent.clearScreen();
            //Adds all of the necessary nodes to the screen
            tacticContent.add(tacticContent.formationLabel, 0, 1);
            tacticContent.add(tacticContent.formation, 1, 1);
            tacticContent.add(tacticContent.playStyleLabel, 0, 2);
            tacticContent.add(tacticContent.playStyle, 1, 2);
        });
    }

    public void clearTactic(){
        if(tacticContent.positionOneCB.getValue() != null){
            tacticContent.clear1();
        }

        if(tacticContent.positionTwoCB.getValue() != null){
            tacticContent.clear2();
        }

        if(tacticContent.positionThreeCB.getValue() != null){
            tacticContent.clear3();
        }

        if(tacticContent.positionFourCB.getValue() != null){
            tacticContent.clear4();
        }

        if(tacticContent.positionFiveCB.getValue() != null){
            tacticContent.clear5();
        }

        if(tacticContent.positionSixCB.getValue() != null){
            tacticContent.clear6();
        }

        if(tacticContent.positionSevenCB.getValue() != null){
            tacticContent.clear7();
        }

        if(tacticContent.positionEightCB.getValue() != null){
            tacticContent.clear8();
        }

        if(tacticContent.positionNineCB.getValue() != null){
            tacticContent.clear9();
        }

        if(tacticContent.positionTenCB.getValue() != null){
            tacticContent.clear10();
        }

        if(tacticContent.positionElevenCB.getValue() != null){
            tacticContent.clear11();
        }
    }

    public static void initNewGame(){
        //Schedule all matches for the league
        LeagueTableEntry.initLeagueTable();

        for(String league : readUniqueLeagues()){
            Schedule schedule = new Schedule(league);
            schedule.createSchedule();
            schedule.writeMatches();
        }
        for(Team team : readAllTeams("")){
            Tactic tactic = generateBestTactic(team.getTeamCode());
            writeTactic(tactic);
            for(Match match : readAllMatches("WHERE homeTeamCode = '" + team.getTeamCode() + "'")){
                match.setHomeTacticCode(tactic.getTacticCode());
                updateMatch(match);
            }
            for(Match match : readAllMatches("WHERE awayTeamCode = '" + team.getTeamCode() + "'")){
                match.setAwayTacticCode(tactic.getTacticCode());
                updateMatch(match);
            }
        }

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

                String saveName = SaveBox.display("Save Tactic", "Enter the tactic name");

                //Write the tactic to the database
                System.out.println(tacticContent.positionTwoCB.getValue().getPlayerCode());
                StartingXI startingXI = new StartingXI(tacticContent.positionOneCB.getValue().getPlayerCode(), tacticContent.positionTwoCB.getValue().getPlayerCode(), tacticContent.positionThreeCB.getValue().getPlayerCode(), tacticContent.positionFourCB.getValue().getPlayerCode(), tacticContent.positionFiveCB.getValue().getPlayerCode(), tacticContent.positionSixCB.getValue().getPlayerCode(), tacticContent.positionSevenCB.getValue().getPlayerCode(), tacticContent.positionEightCB.getValue().getPlayerCode(), tacticContent.positionNineCB.getValue().getPlayerCode(), tacticContent.positionTenCB.getValue().getPlayerCode(), tacticContent.positionElevenCB.getValue().getPlayerCode());
                writeStartingXI(startingXI);


                //Reads what formation has been selected
                String selectedFormation = tacticContent.formation.getValue();
                Tactic tactic = new Tactic(startingXI.getStartingXICode(),tacticContent.attackingScore, tacticContent.creativeScore, tacticContent.defensiveScore,selectedFormation,tacticContent.playStyle.getValue(), saveName);
                //Write the tactic to the database
                writeTactic(tactic);
                //Pop out to say tactic saved
                AlertBox.display("Tactic Saved", "Your tactic " + tactic.getName() + " has been saved!");
            }
        });
    }

    //Adds the action listener for the load tactic button
    public void loadTacticButtonListener(){
        tacticMenu.loadTacticButton.setOnAction(e -> {
            clearTactic();
            Tactic loadedTactic = LoadBox.display("Load Tactic", "Choose a tactic to load");
            tacticContent.formation.setValue(loadedTactic.getFormation());

            tacticContent.playStyle.setValue(loadedTactic.getPlayStyle());
            StartingXI startingXI = readStartingXI(loadedTactic.getStartingXICode());
            tacticContent.positionOneCB.setValue(Player.readPlayer(startingXI.getPlayer1()));
            tacticContent.positionTwoCB.setValue(Player.readPlayer(startingXI.getPlayer2()));
            tacticContent.positionThreeCB.setValue(Player.readPlayer(startingXI.getPlayer3()));
            tacticContent.positionFourCB.setValue(Player.readPlayer(startingXI.getPlayer4()));
            tacticContent.positionFiveCB.setValue(Player.readPlayer(startingXI.getPlayer5()));
            tacticContent.positionSixCB.setValue(Player.readPlayer(startingXI.getPlayer6()));
            tacticContent.positionSevenCB.setValue(Player.readPlayer(startingXI.getPlayer7()));
            tacticContent.positionEightCB.setValue(Player.readPlayer(startingXI.getPlayer8()));
            tacticContent.positionNineCB.setValue(Player.readPlayer(startingXI.getPlayer9()));
            tacticContent.positionTenCB.setValue(Player.readPlayer(startingXI.getPlayer10()));
            tacticContent.positionElevenCB.setValue(Player.readPlayer(startingXI.getPlayer11()));

            tacticContent.updateStatLabels();
            tacticContent.updatePlayerLabels();
        });
    }


    public void autoPickButtonListener(){
        tacticMenu.autoPickButton.setOnAction(e -> {
            clearTactic();
            String teamCode = readCurrentTeam();
            System.out.println("Team Code: " + teamCode);
            Tactic tactic = generateBestTactic(teamCode);

            tacticContent.playStyle.setValue(tactic.getPlayStyle());
            tacticContent.formation.setValue(tactic.getFormation());

            StartingXI startingXI = readStartingXI(tactic.getStartingXICode());
            tacticContent.positionOneCB.setValue(Player.readPlayer(startingXI.getPlayer1()));
            tacticContent.positionTwoCB.setValue(Player.readPlayer(startingXI.getPlayer2()));
            tacticContent.positionThreeCB.setValue(Player.readPlayer(startingXI.getPlayer3()));
            tacticContent.positionFourCB.setValue(Player.readPlayer(startingXI.getPlayer4()));
            tacticContent.positionFiveCB.setValue(Player.readPlayer(startingXI.getPlayer5()));
            tacticContent.positionSixCB.setValue(Player.readPlayer(startingXI.getPlayer6()));
            tacticContent.positionSevenCB.setValue(Player.readPlayer(startingXI.getPlayer7()));
            tacticContent.positionEightCB.setValue(Player.readPlayer(startingXI.getPlayer8()));
            tacticContent.positionNineCB.setValue(Player.readPlayer(startingXI.getPlayer9()));
            tacticContent.positionTenCB.setValue(Player.readPlayer(startingXI.getPlayer10()));
            tacticContent.positionElevenCB.setValue(Player.readPlayer(startingXI.getPlayer11()));

            tacticContent.updateStatLabels();
            tacticContent.updatePlayerLabels();
        });
    }
}
