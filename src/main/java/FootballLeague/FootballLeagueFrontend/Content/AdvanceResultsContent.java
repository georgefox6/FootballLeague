package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Match;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class AdvanceResultsContent extends GridPane {
    Label gameWeek;
    public Button doneButton;
    HBox resultsLabels;

    public AdvanceResultsContent(){
        doneButton = new Button("Done");
        gameWeek = new Label("Week: ");
        resultsLabels = new HBox();
        VBox vbox = new VBox();
        vbox.getChildren().add(gameWeek);
        vbox.getChildren().add(resultsLabels);
        vbox.getChildren().add(doneButton);
        this.getChildren().add(vbox);
        update();
    }

    public void update(){
        resultsLabels.getChildren().clear();

        ArrayList<Match> currentFixtures = new ArrayList<>();
        try {
            String currentGameWeek =  GameState.readGameWeek(GameState.readSaveName());
            gameWeek.setText("Week " + currentGameWeek + " results: ");
            currentFixtures = Match.readAllMatches("WHERE date='" + currentGameWeek + "'");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Couldn't find the current game week");
        }

        for(Match match : currentFixtures){
            VBox temp = new VBox();
            //TODO this should probably be changed to the team name rather than the team code
            temp.getChildren().addAll(new Label(match.getHomeTeamCode() + " " + match.getScore() + " " + match.getAwayTeamCode()));
            resultsLabels.getChildren().add(temp);
        }
    }
}
