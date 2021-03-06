package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ScoutingContent extends TableView {

    public static Logger logger = LogManager.getLogger("com.josh");
    
    TableColumn<Player, String> forenameColumn;
    TableColumn<Player, String> surnameColumn;
    TableColumn<Player, Boolean> injuryColumn;
    TableColumn<Player, String> teamName;

    public ScoutingContent() {
        forenameColumn = new TableColumn<>("Forename");
        surnameColumn = new TableColumn<>("Surname");
        injuryColumn = new TableColumn<>("Injury Status");
        teamName = new TableColumn<>("Team Name");

        //Set the size of each column
        forenameColumn.setMinWidth(180);
        surnameColumn.setMinWidth(180);
        injuryColumn.setMinWidth(180);
        teamName.setMinWidth(180);

        forenameColumn.setCellValueFactory(new PropertyValueFactory<>("forename"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        injuryColumn.setCellValueFactory(new PropertyValueFactory<>("injuryStatus"));
        teamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        setItems(getAllPlayers());
        getColumns().addAll(forenameColumn, surnameColumn, injuryColumn, teamName);
        addShortlistButton();


    }

    public ObservableList<Player> getAllPlayers() {
        ArrayList<Player> players = Player.readAllPlayers(" ");
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        return playerList;
    }

    public void addShortlistButton(){
        TableColumn<Player, Void> colBtn = new TableColumn<>("Shortlist");
        Callback<TableColumn<Player, Void>, TableCell<Player, Void>> cellFactory = new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(final TableColumn<Player, Void> param) {
                final TableCell<Player, Void> cell = new TableCell<Player, Void>() {

                    private final Button btn = new Button("Add to Shortlist");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Player player = getTableView().getItems().get(getIndex());
                            //TODO add player to shortlist
                            logger.info("selectedPlayer: " + player);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);

        this.getColumns().add(colBtn);
    }

}
