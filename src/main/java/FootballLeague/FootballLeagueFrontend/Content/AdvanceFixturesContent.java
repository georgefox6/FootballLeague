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

public class AdvanceFixturesContent extends GridPane {
    Label gameWeek;
    public Button nextButton;
    HBox fixtureLabels;

    public AdvanceFixturesContent(){
        nextButton = new Button("Next");
        gameWeek = new Label("Week: ");
        fixtureLabels = new HBox();
        VBox vbox = new VBox();
        vbox.getChildren().add(gameWeek);
        vbox.getChildren().add(fixtureLabels);
        vbox.getChildren().add(nextButton);
        this.getChildren().add(vbox);
        update();
    }

    public void update(){
        fixtureLabels.getChildren().clear();

        ArrayList<Match> currentFixtures = new ArrayList<>();
        try {
            String currentGameWeek =  GameState.readGameWeek(GameState.readSaveName());
            gameWeek.setText("Week " + currentGameWeek + " fixtures: ");
            currentFixtures = Match.readAllMatches("WHERE date='" + currentGameWeek + "'");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Couldn't find the current game week");
        }

        for(Match match : currentFixtures){
            HBox temp = new HBox();
            //TODO this should probably be changed to the team name rather than the team code
            temp.getChildren().add(new Label(match.getHomeTeamCode() + " VS " + match.getAwayTeamCode()));
            temp.setPrefWidth(100);
            fixtureLabels.getChildren().add(temp);
        }
    }
}
