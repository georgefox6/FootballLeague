package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Match;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

//TODO maybe this shouldn't be GridPane
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
        String currentGameWeek =  GameState.readGameWeek(GameState.readSaveName());
        gameWeek.setText("Week " + currentGameWeek + " fixtures: ");
        currentFixtures = Match.readAllMatches("WHERE date='" + currentGameWeek + "'");

        for(Match match : currentFixtures){
            HBox temp = new HBox();
            temp.getChildren().add(new Label((match.getHomeTeamName() + "\n" + " VS \n" + match.getAwayTeamName() + "\n")));
            temp.setPrefWidth(120);
            fixtureLabels.getChildren().add(temp);
        }
    }
}
