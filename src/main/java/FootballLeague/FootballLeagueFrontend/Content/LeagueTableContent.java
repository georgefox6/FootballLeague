package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.LeagueTableEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

import static FootballLeague.FootballLeagueBackend.GameState.readTeamLeague;
import static FootballLeague.FootballLeagueBackend.LeagueTableEntry.readAllLeagueTableEntries;

public class LeagueTableContent extends TableView {


    TableColumn<LeagueTableEntry, Integer> positionColumn;
    TableColumn<LeagueTableEntry, Integer> teamNameColumn;
    TableColumn<LeagueTableEntry, Integer> playedColumn;
    TableColumn<LeagueTableEntry, Integer> wonColumn;
    TableColumn<LeagueTableEntry, Integer> drawnColumn;
    TableColumn<LeagueTableEntry, Integer> lostColumn;
    TableColumn<LeagueTableEntry, Integer> goalsScoredColumn;
    TableColumn<LeagueTableEntry, Integer> goalsConcededColumn;
    TableColumn<LeagueTableEntry, Integer> goalDifferenceColumn;
    TableColumn<LeagueTableEntry, Integer> pointsColumn;

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
        getSortOrder().add(positionColumn);
    }

    public ObservableList<LeagueTableEntry> getLeagueTable(){
        String league = readTeamLeague();

        ArrayList<LeagueTableEntry> leaguePositions = readAllLeagueTableEntries("WHERE league='" + league + "'");
        return FXCollections.observableArrayList(leaguePositions);
    }



    public void updateLeagueTable(){
        setItems(getLeagueTable());
        getSortOrder().add(positionColumn);
    }
}
