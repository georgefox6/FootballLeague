package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Match;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class AdvanceResultsContent extends VBox {
    Label gameWeek;
    public Button doneButton;
    VBox resultsLabels;

    public AdvanceResultsContent(){
        doneButton = new Button("Done");
        gameWeek = new Label("Week: ");
        resultsLabels = new VBox();
        this.getChildren().add(gameWeek);
        this.getChildren().add(resultsLabels);
        this.getChildren().add(doneButton);
        update();
    }

    public void update(){
        resultsLabels.getChildren().clear();

        String currentGameWeek =  GameState.readGameWeek(GameState.readSaveName());
        gameWeek.setText("Week " + currentGameWeek + " results: ");
        ArrayList<Match> currentFixtures = Match.readAllMatches("WHERE date='" + currentGameWeek + "'");

        for(Match match : currentFixtures){
            resultsLabels.getChildren().add(new Label(match.getHomeTeamName() + " " + match.getScore() +  " " + match.getAwayTeamName()));
        }
    }
}