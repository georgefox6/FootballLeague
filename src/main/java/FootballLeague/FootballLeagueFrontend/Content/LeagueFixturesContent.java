package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Match;
import FootballLeague.FootballLeagueBackend.Team;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static FootballLeague.FootballLeagueBackend.GameState.readSaveName;
import static FootballLeague.FootballLeagueBackend.GameState.readGameWeek;
import static FootballLeague.FootballLeagueBackend.Match.getNumWeeks;
import static FootballLeague.FootballLeagueBackend.Match.readAllMatches;
import static FootballLeague.FootballLeagueBackend.Team.readAllTeams;

public class LeagueFixturesContent extends VBox {
    ComboBox<Integer> gameWeek;
    ComboBox<Team> team;
    VBox fixtures;

    public LeagueFixturesContent(){

        //Sets some spacing to make the screen look better
        setPadding(new Insets(0, 10, 0, 10));

        gameWeek = new ComboBox<>();
        team = new ComboBox<>();
        fixtures = new VBox();
        updateContent();

        gameWeek.setOnAction(e -> {
            if(gameWeek.getValue() != null){
                fixtures.getChildren().clear();
                for(Match result : readAllMatches("WHERE date='" + gameWeek.getValue() + "';")){
                    String labelContents = padRight(result.getHomeTeamName(), 20) + result.getScore() +  " " + padRight(result.getAwayTeamName(), 20);
                    Label label = new Label(labelContents);
                    fixtures.getChildren().add(label);
                }
                team.setValue(null);
            }

        });

        team.setOnAction(e -> {
            if(team.getValue() != null){
                fixtures.getChildren().clear();
                //TODO order the fixtures by game week
                for(Match result : readAllMatches("WHERE homeTeamCode='" + team.getValue().getTeamCode() + "' or awayTeamCode='" + team.getValue().getTeamCode() + "' and score <> null;")){
                    String labelContents = result.getDate() + " : " + padRight(result.getHomeTeamName(), 20) + result.getScore() + " " + padRight(result.getAwayTeamName(), 20);
                    Label label = new Label(labelContents);
                    fixtures.getChildren().add(label);
                }
                gameWeek.setValue(null);
            }
        });



        HBox resultSelector = new HBox();

        Label gameWeekLabel = new Label("Game week:  ");

        Label teamLabel = new Label("  or Team:  ");

        resultSelector.getChildren().addAll(gameWeekLabel, gameWeek, teamLabel, team);

        this.getChildren().add(resultSelector);
        this.getChildren().add(fixtures);
    }

    public void updateContent(){
        //Remove the existing numbers from the combo box
        gameWeek.getItems().clear();
        //Remove the existing teams from the combo box
        team.getItems().clear();
        //Remove the existing results from the screen
        fixtures.getChildren().clear();
        //Fill the combo box with all of the game weeks containing results
        for(int i = 1; i < Integer.parseInt(readGameWeek(readSaveName())); i++){
            gameWeek.getItems().add(i);
        }
        for(int i = getNumWeeks(); i > Integer.parseInt(readGameWeek(readSaveName())); i--){
            gameWeek.getItems().add(i);
        }
        gameWeek.getItems().add(null);
        //Fill the team combo box with all of the teams
        for(Team t : readAllTeams("WHERE league='" + GameState.readTeamLeague() + "';")){
            team.getItems().add(t);
        }
        team.getItems().add(null);
    }

    //TODO the padding doesn't work currently, changes are needed to this method
    public static String padRight(String s, int n) {
        String str = String.format("%1$-" + n + "s", s);
        return str;
    }
}
