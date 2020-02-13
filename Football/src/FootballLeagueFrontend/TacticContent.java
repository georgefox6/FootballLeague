package FootballLeagueFrontend;

import FootballLeagueBackend.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

import static FootballLeagueBackend.Database.readPlayersTeam;

public class TacticContent extends GridPane {
    //Formation selector
    ComboBox<String> formation;
    Label formationLabel;

    //Play style selector
    ComboBox<String> playStyle;
    Label playStyleLabel;

    //Adds all of the ComboBoxes to select the players for each position
    ComboBox<Player> positionOneCB;
    ComboBox<Player> positionTwoCB;
    ComboBox<Player> positionThreeCB;
    ComboBox<Player> positionFourCB;
    ComboBox<Player> positionFiveCB;
    ComboBox<Player> positionSixCB;
    ComboBox<Player> positionSevenCB;
    ComboBox<Player> positionEightCB;
    ComboBox<Player> positionNineCB;
    ComboBox<Player> positionTenCB;
    ComboBox<Player> positionElevenCB;

    //Creates labels for every possible position which will be added to the screen depending on the chosen formation
    Label gkLabel = new Label("GK:");
    Label rbLabel = new Label("RB:");
    Label rcbLabel = new Label("RCB:");
    Label cbLabel = new Label("CB:");
    Label lcbLabel = new Label("LCB:");
    Label lbLabel = new Label("LB:");
    Label rcmLabel = new Label("RCM:");
    Label cmLabel = new Label("CM:");
    Label lcmLabel = new Label("LCM:");
    Label rmLabel = new Label("RM:");
    Label lmLabel = new Label("LM:");
    Label rwLabel = new Label("RW:");
    Label lwLabel = new Label("LW:");
    Label rstLabel = new Label("RST:");
    Label lstLabel = new Label("LST:");
    Label stLabel = new Label("ST:");

    //Creates the labels for the right hand side of the screen
    Label p1;
    Label p2;
    Label p3;
    Label p4;
    Label p5;
    Label p6;
    Label p7;
    Label p8;
    Label p9;
    Label p10;
    Label p11;

    //Constructor for the tactic content
    public TacticContent(){
        //Set min column width
         for (int i = 0; i < 10; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setMinWidth(50);
            getColumnConstraints().add(column);
         }

        //Sets some spacing to make the screen look better
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(0, 10, 0, 10));

        p1 = new Label("*");
        p2 = new Label("*");
        p3 = new Label("*");
        p4 = new Label("*");
        p5 = new Label("*");
        p6 = new Label("*");
        p7 = new Label("*");
        p8 = new Label("*");
        p9 = new Label("*");
        p10 = new Label("*");
        p11 = new Label("*");

        //Formation selector
        formation = new ComboBox<>(FXCollections.observableArrayList("4-4-2", "4-3-3", "5-3-2", "3-4-3"));
        add(formation, 1, 1);
        formationLabel = new Label("Formation:");
        add(formationLabel, 0, 1);

        //PlayStyle selector
        playStyle = new ComboBox<>(FXCollections.observableArrayList("Tiki Taka", "Gegenpress", "Counter Attack", "Park the bus"));
        add(playStyle, 3, 1);
        playStyleLabel = new Label("Play style:");
        add(playStyleLabel, 2, 1);

        //Calls getPlayersFromTeam method to add player names to the combo boxes
        ObservableList<Player> playerList = getPlayersFromTeam();
        positionOneCB = new ComboBox<>(playerList);
        positionTwoCB = new ComboBox<>(playerList);
        positionThreeCB = new ComboBox<>(playerList);
        positionFourCB = new ComboBox<>(playerList);
        positionFiveCB = new ComboBox<>(playerList);
        positionSixCB = new ComboBox<>(playerList);
        positionSevenCB = new ComboBox<>(playerList);
        positionEightCB = new ComboBox<>(playerList);
        positionNineCB = new ComboBox<>(playerList);
        positionTenCB = new ComboBox<>(playerList);
        positionElevenCB = new ComboBox<>(playerList);

        //Action listener for the formation combo box which will set the correct labels
        formation.setOnAction(e -> {
            clearScreen();
            switch(formation.getValue()){
                case "4-4-2":
                    setFormation442();
                    break;
                case "4-3-3":
                    setFormation433();
                    break;
                case "5-3-2":
                    setFormation532();
                    break;
                case "3-4-3":
                    setFormation343();
                    break;
            }
        });

        //Replaces the * with player name when a player is selected in the combo box
        //Checks that the value of the combo box is not null as this will cause an error
        positionOneCB.setOnAction(e -> {
            if(positionOneCB.getValue() != null){
                p1.setText(positionOneCB.getValue().toString());
            }
        });
        positionTwoCB.setOnAction(e -> {
            if(positionTwoCB.getValue() != null){
                p2.setText(positionTwoCB.getValue().toString());
            }
        });
        positionThreeCB.setOnAction(e -> {
            if(positionThreeCB.getValue() != null){
                p3.setText(positionThreeCB.getValue().toString());
            }
        });
        positionFourCB.setOnAction(e -> {
            if(positionFourCB.getValue() != null){
                p4.setText(positionFourCB.getValue().toString());
            }
        });
        positionFiveCB.setOnAction(e -> {
            if(positionFiveCB.getValue() != null){
                p5.setText(positionFiveCB.getValue().toString());
            }
        });
        positionSixCB.setOnAction(e -> {
            if(positionSixCB.getValue() != null){
                p6.setText(positionSixCB.getValue().toString());
            }
        });
        positionSevenCB.setOnAction(e -> {
            if(positionSevenCB.getValue() != null){
                p7.setText(positionSevenCB.getValue().toString());
            }
        });
        positionEightCB.setOnAction(e -> {
            if(positionEightCB.getValue() != null){
                p8.setText(positionEightCB.getValue().toString());
            }
        });
        positionNineCB.setOnAction(e -> {
            if(positionNineCB.getValue() != null){
                p9.setText(positionNineCB.getValue().toString());
            }
        });
        positionTenCB.setOnAction(e -> {
            if(positionTenCB.getValue() != null){
                p10.setText(positionTenCB.getValue().toString());
            }
        });
        positionElevenCB.setOnAction(e -> {
            if(positionElevenCB.getValue() != null){
                p11.setText(positionElevenCB.getValue().toString());
            }
        });

    }

    //Clear the screen of everything
    public void clearScreen(){
        this.getChildren().clear();
    }

    public void setFormation442(){
        //Adds the formation and play style selectors
        this.add(formationLabel, 0, 1);
        this.add(formation, 1, 1);
        this.add(playStyleLabel, 2, 1);
        this.add(playStyle, 3, 1);

        //Add the labels to the screen
        this.addColumn(0, gkLabel, rbLabel, rcbLabel, lcbLabel, lbLabel, rmLabel, rcmLabel, lcmLabel, lmLabel, rstLabel, lstLabel);
        //Add the combo boxes to the screen
        this.addColumn(1,positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);

        //Add the *'s to the right side of the screen
        add(p1, 7, 10);
        add(p2, 9, 8);
        add(p3, 8, 8);
        add(p4, 6, 8);
        add(p5, 5, 8);
        add(p6, 9, 6);
        add(p7, 8, 6);
        add(p8, 6, 6);
        add(p9, 5, 6);
        add(p10, 8, 4);
        add(p11, 6, 4);
    }

    public void setFormation433(){
        //Adds the formation and play style selectors
        this.add(formationLabel, 0, 1);
        this.add(formation, 1, 1);
        this.add(playStyleLabel, 2, 1);
        this.add(playStyle, 3, 1);

        //Add the labels to the screen
        this.addColumn(0, gkLabel, rbLabel, rcbLabel, lcbLabel, lbLabel, rcmLabel, cmLabel, lcmLabel, rwLabel, stLabel, lwLabel);
        //Add the combo boxes to the screen
        this.addColumn(1,positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);

        //Add the *'s to the right side of the screen
        add(p1, 7, 10);
        add(p2, 9, 8);
        add(p3, 8, 8);
        add(p4, 6, 8);
        add(p5, 5, 8);
        add(p6, 8, 6);
        add(p7, 7, 6);
        add(p8, 6, 6);
        add(p9, 9, 4);
        add(p10, 7, 4);
        add(p11, 5, 4);
    }

    public void setFormation532(){
        //Adds the formation and play style selectors
        this.add(formationLabel, 0, 1);
        this.add(formation, 1, 1);
        this.add(playStyleLabel, 2, 1);
        this.add(playStyle, 3, 1);

        //Add the labels to the screen
        this.addColumn(0, gkLabel, rbLabel, rcbLabel, cbLabel, lcbLabel, lbLabel, rcmLabel, cmLabel, lcmLabel, rstLabel, lstLabel);
        //Add the combo boxes to the screen
        this.addColumn(1,positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);

        //Add the *'s to the right side of the screen
        add(p1, 7, 10);
        add(p2, 9, 8);
        add(p3, 8, 8);
        add(p4, 7, 8);
        add(p5, 6, 8);
        add(p6, 5, 8);
        add(p7, 8, 6);
        add(p8, 7, 6);
        add(p9, 6, 6);
        add(p10, 8, 4);
        add(p11, 6, 4);
    }

    public void setFormation343(){
        //Adds the formation and play style selectors
        this.add(formationLabel, 0, 1);
        this.add(formation, 1, 1);
        this.add(playStyleLabel, 2, 1);
        this.add(playStyle, 3, 1);

        //Add the labels to the screen
        this.addColumn(0, gkLabel, rcbLabel, cbLabel, lcbLabel, rmLabel, rcmLabel, lcmLabel, lmLabel, rwLabel, stLabel, lwLabel);
        //Add the combo boxes to the screen
        this.addColumn(1,positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);

        //Add the *'s to the right side of the screen
        add(p1, 7, 10);
        add(p2, 8, 8);
        add(p3, 7, 8);
        add(p4, 6, 8);
        add(p5, 9, 6);
        add(p6, 8, 6);
        add(p7, 6, 6);
        add(p8, 5, 6);
        add(p9, 9, 4);
        add(p10, 7, 4);
        add(p11, 5, 4);
    }

    public ObservableList<Player> getPlayersFromTeam(){
        //TODO This needs changing to the users team code from a config file or something
        ArrayList<Player> players = readPlayersTeam("006SPU");
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        return playerList;
    }
}