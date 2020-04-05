package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Match;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        String currentGameWeek =  GameState.readGameWeek(GameState.readSaveName());
        gameWeek.setText("Week " + currentGameWeek + " results: ");
        currentFixtures = Match.readAllMatches("WHERE date='" + currentGameWeek + "'");

        for(Match match : currentFixtures){
            VBox temp = new VBox();
            temp.getChildren().addAll(new Label(match.getHomeTeamName() + "\n " + match.getScore() +  "\n " + match.getAwayTeamName()+ "\n"));
            temp.setPrefWidth(120);
            resultsLabels.getChildren().add(temp);
        }
    }
}
