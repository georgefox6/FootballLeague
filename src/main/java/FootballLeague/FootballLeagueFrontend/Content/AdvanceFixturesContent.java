package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Match;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class AdvanceFixturesContent extends VBox {
    Label gameWeek;
    public Button nextButton;
    VBox fixtureLabels;

    public AdvanceFixturesContent(){
        nextButton = new Button("Next");
        gameWeek = new Label("Week: ");
        fixtureLabels = new VBox();
        this.getChildren().add(gameWeek);
        this.getChildren().add(fixtureLabels);
        this.getChildren().add(nextButton);
        update();
    }

    public void update(){
        fixtureLabels.getChildren().clear();

        String currentGameWeek =  GameState.readGameWeek(GameState.readSaveName());
        gameWeek.setText("Week " + currentGameWeek + " fixtures: ");
        ArrayList<Match> currentFixtures = Match.readAllMatches("WHERE date='" + currentGameWeek + "'");

        for(Match match : currentFixtures){
            fixtureLabels.getChildren().add(new Label((match.getHomeTeamName() + " VS " + match.getAwayTeamName())));
        }
    }
}