package FootballLeague.FootballLeagueFrontend;

import FootballLeague.FootballLeagueBackend.Player;
import FootballLeague.FootballLeagueBackend.Tactic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LoadBox {

    static Tactic tacticLoaded;

    public static Tactic display(String title, String message) {
        Stage window = new Stage();

        window.setTitle(title);
        window.setMaxWidth(200);

        //Makes it so you have to deal with the alert box before you can interact with the rest of the program
        window.initModality(Modality.APPLICATION_MODAL);

        //Creates the labels and button on the alert box
        Label label = new Label(message);


        ComboBox loadSelector = new ComboBox(readSaveGames());

        loadSelector.setOnAction(e -> {
            window.close();
            tacticLoaded = (Tactic) loadSelector.getValue();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, loadSelector);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return tacticLoaded;
    }


    public static ObservableList<Tactic> readSaveGames(){
        ArrayList<Tactic> tactics = Tactic.readAllTactics("WHERE name <> ''");
        ObservableList<Tactic> tacticList = FXCollections.observableArrayList(tactics);
        return tacticList;
    }
}
