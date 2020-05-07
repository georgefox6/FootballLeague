package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class FirstTeamContent extends TableView {
    
    TableColumn<Player, String> forenameColumn;
    TableColumn<Player, String> surnameColumn;
    TableColumn<Player, Double> attackingStatColumn;
    TableColumn<Player, Double> creativityStatColumn;
    TableColumn<Player, Double> defensiveStatColumn;

    public FirstTeamContent() {
        forenameColumn = new TableColumn<>("Forename");
        surnameColumn = new TableColumn<>("Surname");
        attackingStatColumn = new TableColumn<>("Attacking Stat");
        creativityStatColumn = new TableColumn<>("Creativity Stat");
        defensiveStatColumn = new TableColumn<>("Defensive Stat");

        //Set the size of each column
        forenameColumn.setMinWidth(150);
        surnameColumn.setMinWidth(150);
        attackingStatColumn.setMinWidth(150);
        creativityStatColumn.setMinWidth(150);
        defensiveStatColumn.setMinWidth(150);

        forenameColumn.setCellValueFactory(new PropertyValueFactory<>("forename"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        attackingStatColumn.setCellValueFactory(new PropertyValueFactory<>("attackingStat"));
        creativityStatColumn.setCellValueFactory(new PropertyValueFactory<>("creativityStat"));
        defensiveStatColumn.setCellValueFactory(new PropertyValueFactory<>("defensiveStat"));

        setItems(getPlayersFromTeam());
        getColumns().addAll(forenameColumn, surnameColumn, attackingStatColumn, creativityStatColumn, defensiveStatColumn);
    }

    public ObservableList<Player> getPlayersFromTeam() {
        ArrayList<Player> players = null;
        try {
            players = Player.readAllPlayers("WHERE teamCode='" + GameState.readTeam(GameState.readSaveName()) + "'");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        //     playerList.add(null);
        return playerList;
    }
}


