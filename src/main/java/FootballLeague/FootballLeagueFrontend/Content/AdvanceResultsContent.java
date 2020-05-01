package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Match;
import FootballLeague.FootballLeagueBackend.Team;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class AdvanceResultsContent extends VBox {
    Label gameWeek;
    public Button doneButton;
    TabPane tabPane;

    public AdvanceResultsContent(){
        doneButton = new Button("Done");
        gameWeek = new Label("Week: ");
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(UNAVAILABLE);
        this.getChildren().add(gameWeek);
        this.getChildren().add(tabPane);
        this.getChildren().add(doneButton);
        update();
    }

    public void update(){
        String currentGameWeek =  GameState.readGameWeek(GameState.readSaveName());
        gameWeek.setText("Week " + currentGameWeek + " results: ");
        ArrayList<Match> currentFixtures = Match.readAllMatches("WHERE date='" + currentGameWeek + "'");

        //Clear tabPane of previous tabs
        tabPane.getTabs().clear();

        //Get a list of all the different leagues
        Set<String> leagues = new HashSet<>();

        for(Team team : Team.readAllTeams("")){
            leagues.add(team.getLeague());
        }

        //Iterate each league and add make new VBOX for labels with team vs team
        for(String league : leagues) {
            ArrayList<Match> results = Match.readAllMatches("WHERE date='" + currentGameWeek + "' and homeTeamCode in (SELECT teamCode from team where league='" + league + "')");
            VBox leagueResults = new VBox();

            for(Match result : results){
                leagueResults.getChildren().add(new Label(result.getHomeTeamName() + " " + result.getScore() +  " " + result.getAwayTeamName()));
            }

            //create new tab with league name, new vbox
            Tab tab = new Tab(league,leagueResults);

            //add tab to tab pane
            tabPane.getTabs().add(tab);
        }
    }
}