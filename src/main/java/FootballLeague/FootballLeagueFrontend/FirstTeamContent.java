package FootballLeague.FootballLeagueFrontend;

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
    TableColumn<Player, Boolean> injuryColumn;

    public FirstTeamContent() {
        forenameColumn = new TableColumn<>("Forename");
        surnameColumn = new TableColumn<>("Surname");
        injuryColumn = new TableColumn<>("Injury Status");

        //Set the size of each column
        forenameColumn.setMinWidth(200);
        surnameColumn.setMinWidth(200);
        injuryColumn.setMinWidth(200);

        forenameColumn.setCellValueFactory(new PropertyValueFactory<>("forename"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        injuryColumn.setCellValueFactory(new PropertyValueFactory<>("injuryStatus"));

        setItems(getPlayersFromTeam());
        getColumns().addAll(forenameColumn, surnameColumn, injuryColumn);
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


