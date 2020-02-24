package FootballLeagueFrontend;

import FootballLeagueBackend.Database;
import FootballLeagueBackend.LeaguePosition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class LeagueTableContent extends TableView {
    TableColumn<LeaguePosition, Integer> positionColumn;
    TableColumn<LeaguePosition, Integer> teamNameColumn;
    TableColumn<LeaguePosition, Integer> playedColumn;
    TableColumn<LeaguePosition, Integer> wonColumn;
    TableColumn<LeaguePosition, Integer> drawnColumn;
    TableColumn<LeaguePosition, Integer> lostColumn;
    TableColumn<LeaguePosition, Integer> goalsScoredColumn;
    TableColumn<LeaguePosition, Integer> goalsConcededColumn;
    TableColumn<LeaguePosition, Integer> goalDifferenceColumn;
    TableColumn<LeaguePosition, Integer> pointsColumn;

    public LeagueTableContent(){
        positionColumn = new TableColumn<>("Position");
        teamNameColumn = new TableColumn<>("Team Name");
        playedColumn = new TableColumn<>("Played");
        wonColumn = new TableColumn<>("Won");
        drawnColumn = new TableColumn<>("Drawn");
        lostColumn = new TableColumn<>("Lost");
        goalsScoredColumn = new TableColumn<>("Goals Scored");
        goalsConcededColumn = new TableColumn<>("Goals Conceded");
        goalDifferenceColumn = new TableColumn<>("Goal Difference");
        pointsColumn = new TableColumn<>("Points");

        //Set the size of each column
        positionColumn.setMinWidth(50);
        teamNameColumn.setMinWidth(50);
        playedColumn.setMinWidth(50);
        wonColumn.setMinWidth(50);
        drawnColumn.setMinWidth(50);
        lostColumn.setMinWidth(50);
        goalsScoredColumn.setMinWidth(50);
        goalsConcededColumn.setMinWidth(50);
        goalDifferenceColumn.setMinWidth(50);
        pointsColumn.setMinWidth(50);

        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        playedColumn.setCellValueFactory(new PropertyValueFactory<>("played"));
        wonColumn.setCellValueFactory(new PropertyValueFactory<>("won"));
        drawnColumn.setCellValueFactory(new PropertyValueFactory<>("drawn"));
        lostColumn.setCellValueFactory(new PropertyValueFactory<>("lost"));
        goalsScoredColumn.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        goalsConcededColumn.setCellValueFactory(new PropertyValueFactory<>("goalsConceded"));
        goalDifferenceColumn.setCellValueFactory(new PropertyValueFactory<>("goalDifference"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        setItems(getLeagueTable());
        getColumns().addAll(positionColumn, teamNameColumn, playedColumn, wonColumn, drawnColumn, lostColumn, goalsScoredColumn, goalsConcededColumn, goalDifferenceColumn, pointsColumn);
    }
    public ObservableList<LeaguePosition> getLeagueTable(){
        //TODO This needs to be changed to the league that the player is in
        //TODO this also needs changing to no longer use the database class
        ArrayList<LeaguePosition> leaguePositions = Database.readLeaguePositionLeague("Premier League");
        return FXCollections.observableArrayList(leaguePositions);

    }
}
