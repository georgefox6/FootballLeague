package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.Match;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static FootballLeague.FootballLeagueBackend.GameState.readSaveName;
import static FootballLeague.FootballLeagueBackend.GameState.readGameWeek;
import static FootballLeague.FootballLeagueBackend.Match.readAllMatches;

public class LeagueResultsContent extends VBox {
    ComboBox<Integer> gameWeek;
    VBox results;

    public LeagueResultsContent(){

        //Sets some spacing to make the screen look better
        setPadding(new Insets(0, 10, 0, 10));

        gameWeek = new ComboBox<>();
        results = new VBox();
        updateContent();

        gameWeek.setOnAction(e -> {
            results.getChildren().clear();
            for(Match result : readAllMatches("WHERE date='" + gameWeek.getValue() + "';")){
                String labelContents = padRight(result.getHomeTeamName(), 20) + result.getScore() +  " " + padRight(result.getAwayTeamName(), 20);
                System.out.println(labelContents);
                Label label = new Label(labelContents);
                results.getChildren().add(label);
            }
        });

        this.getChildren().add(gameWeek);
        this.getChildren().add(results);
    }

    public void updateContent(){
        //Remove the existing numbers from the combo box
        gameWeek.getItems().clear();
        //Remove the existing results from the screen
        results.getChildren().clear();
        //Fill the combo box with all of the game weeks containing results
        for(int i = 1; i < Integer.parseInt(readGameWeek(readSaveName())); i++){
            gameWeek.getItems().add(i);
        }
    }

    //TODO the padding doesn't work currently, changes are needed to this method
    public static String padRight(String s, int n) {
        String str = String.format("%1$-" + n + "s", s);
        return str;
    }
}
