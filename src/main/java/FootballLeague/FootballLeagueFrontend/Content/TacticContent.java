package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class TacticContent extends GridPane {

    Label attackingScoreLabel;
    Label creativeScoreLabel;
    Label defensiveScoreLabel;

    public double attackingScore;
    public double creativeScore;
    public double defensiveScore;

    //Formation selector
    public ComboBox<String> formation;
    public Label formationLabel;

    //Play style selector
    public ComboBox<String> playStyle;
    public Label playStyleLabel;

    //Adds all of the ComboBoxes to select the players for each position
    public ComboBox<Player> positionOneCB;
    public ComboBox<Player> positionTwoCB;
    public ComboBox<Player> positionThreeCB;
    public ComboBox<Player> positionFourCB;
    public ComboBox<Player> positionFiveCB;
    public ComboBox<Player> positionSixCB;
    public ComboBox<Player> positionSevenCB;
    public ComboBox<Player> positionEightCB;
    public ComboBox<Player> positionNineCB;
    public ComboBox<Player> positionTenCB;
    public ComboBox<Player> positionElevenCB;

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
    public Label p1;
    public Label p2;
    public Label p3;
    public Label p4;
    public Label p5;
    public Label p6;
    public Label p7;
    public Label p8;
    public Label p9;
    public Label p10;
    public Label p11;

    //These are variables which store the previous player for each of the combo boxes
    Player previousPlayer1;
    Player previousPlayer2;
    Player previousPlayer3;
    Player previousPlayer4;
    Player previousPlayer5;
    Player previousPlayer6;
    Player previousPlayer7;
    Player previousPlayer8;
    Player previousPlayer9;
    Player previousPlayer10;
    Player previousPlayer11;

    //Add buttons to clear player from the combo box
    Button clearButton1;
    Button clearButton2;
    Button clearButton3;
    Button clearButton4;
    Button clearButton5;
    Button clearButton6;
    Button clearButton7;
    Button clearButton8;
    Button clearButton9;
    Button clearButton10;
    Button clearButton11;


    //Constructor for the tactic content
    public TacticContent(){



        attackingScoreLabel = new Label("Attacking Score: ");
        creativeScoreLabel = new Label("Creative Score: ");
        defensiveScoreLabel = new Label("Defensive Score: ");

        clearButton1 = new Button();
        clearButton2 = new Button();
        clearButton3 = new Button();
        clearButton4 = new Button();
        clearButton5 = new Button();
        clearButton6 = new Button();
        clearButton7 = new Button();
        clearButton8 = new Button();
        clearButton9 = new Button();
        clearButton10 = new Button();
        clearButton11 = new Button();

        clearButton1 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton2 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton3 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton4 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton5 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton6 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton7 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton8 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton9 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton10 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");
        clearButton11 = createIconButton("images/delete-icon.png", 18, 18, "delete-button");

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
        add(playStyle, 1, 2);
        playStyleLabel = new Label("Play style:");
        add(playStyleLabel, 0, 2);

        positionOneCB = new ComboBox<>(getPlayersFromTeam());
        positionTwoCB = new ComboBox<>(getPlayersFromTeam());
        positionThreeCB = new ComboBox<>(getPlayersFromTeam());
        positionFourCB = new ComboBox<>(getPlayersFromTeam());
        positionFiveCB = new ComboBox<>(getPlayersFromTeam());
        positionSixCB = new ComboBox<>(getPlayersFromTeam());
        positionSevenCB = new ComboBox<>(getPlayersFromTeam());
        positionEightCB = new ComboBox<>(getPlayersFromTeam());
        positionNineCB = new ComboBox<>(getPlayersFromTeam());
        positionTenCB = new ComboBox<>(getPlayersFromTeam());
        positionElevenCB = new ComboBox<>(getPlayersFromTeam());

        //Action listener for the formation combo box which will set the correct labels
        formation.setOnAction(e -> {
            if(formation.getValue() != null){
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
            }
        });

        //Replaces the * with player name when a player is selected in the combo box
        //Checks that the value of the combo box is not null as this will cause an error
        positionOneCB.setOnAction(e -> {
            if(positionOneCB.getValue() != null){
                p1.setText(positionOneCB.getValue().toString());
                updateCBOne();
                previousPlayer1 = positionOneCB.getValue();
                updateStatLabels();
            }
        });
        positionTwoCB.setOnAction(e -> {
            if(positionTwoCB.getValue() != null){
                p2.setText(positionTwoCB.getValue().toString());
                updateCBTwo();
                previousPlayer2 = positionTwoCB.getValue();
                updateStatLabels();
            }
        });
        positionThreeCB.setOnAction(e -> {
            if(positionThreeCB.getValue() != null){
                p3.setText(positionThreeCB.getValue().toString());
                updateCBThree();
                previousPlayer3 = positionThreeCB.getValue();
                updateStatLabels();
            }
        });
        positionFourCB.setOnAction(e -> {
            if(positionFourCB.getValue() != null){
                p4.setText(positionFourCB.getValue().toString());
                updateCBFour();
                previousPlayer4 = positionFourCB.getValue();
                updateStatLabels();
            }
        });
        positionFiveCB.setOnAction(e -> {
            if(positionFiveCB.getValue() != null){
                p5.setText(positionFiveCB.getValue().toString());
                updateCBFive();
                previousPlayer5 = positionFiveCB.getValue();
                updateStatLabels();
            }
        });
        positionSixCB.setOnAction(e -> {
            if(positionSixCB.getValue() != null){
                p6.setText(positionSixCB.getValue().toString());
                updateCBSix();
                previousPlayer6 = positionSixCB.getValue();
                updateStatLabels();
            }
        });
        positionSevenCB.setOnAction(e -> {
            if(positionSevenCB.getValue() != null){
                p7.setText(positionSevenCB.getValue().toString());
                updateCBSeven();
                previousPlayer7 = positionSevenCB.getValue();
                updateStatLabels();
            }
        });
        positionEightCB.setOnAction(e -> {
            if(positionEightCB.getValue() != null){
                p8.setText(positionEightCB.getValue().toString());
                updateCBEight();
                previousPlayer8 = positionEightCB.getValue();
                updateStatLabels();
            }
        });
        positionNineCB.setOnAction(e -> {
            if(positionNineCB.getValue() != null){
                p9.setText(positionNineCB.getValue().toString());
                updateCBNine();
                previousPlayer9 = positionNineCB.getValue();
                updateStatLabels();
            }
        });
        positionTenCB.setOnAction(e -> {
            if(positionTenCB.getValue() != null){
                p10.setText(positionTenCB.getValue().toString());
                updateCBTen();
                previousPlayer10 = positionTenCB.getValue();
                updateStatLabels();
            }
        });
        positionElevenCB.setOnAction(e -> {
            if(positionElevenCB.getValue() != null){
                p11.setText(positionElevenCB.getValue().toString());
                updateCBEleven();
                previousPlayer11 = positionElevenCB.getValue();
                updateStatLabels();
            }
        });

        //Action listeners for the clear buttons
        clearButton1.setOnAction(e -> {
            if(positionOneCB.getValue() != null){
                clear1();
            }
        });

        clearButton2.setOnAction(e -> {
            if(positionTwoCB.getValue() != null){
                clear2();
            }
        });

        clearButton3.setOnAction(e -> {
            if(positionThreeCB.getValue() != null){
                clear3();
            }
        });


        clearButton4.setOnAction(e -> {
            if(positionFourCB.getValue() != null){
                clear4();
            }
        });

        clearButton5.setOnAction(e -> {
            if(positionFiveCB.getValue() != null){
                clear5();
            }
        });

        clearButton6.setOnAction(e -> {
            if(positionSixCB.getValue() != null){
                clear6();
            }
        });

        clearButton7.setOnAction(e -> {
            if(positionSevenCB.getValue() != null){
                clear7();
            }
        });

        clearButton8.setOnAction(e -> {
            if(positionEightCB.getValue() != null){
                clear8();
            }
        });

        clearButton9.setOnAction(e -> {
            if(positionNineCB.getValue() != null){
                clear9();
            }
        });

        clearButton10.setOnAction(e -> {
            if(positionTenCB.getValue() != null){
                clear10();
            }
        });

        clearButton11.setOnAction(e -> {
            if(positionElevenCB.getValue() != null){
                clear11();
            }
        });

        playStyle.setOnAction(e -> {
            updateStatLabels();
        });
    }

    public Button createIconButton(String imageLocation, int height, int width, String styleClass){
        Image icon = new Image(imageLocation);
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(width);
        iconView.setFitHeight(height);
        Button button = new Button("", iconView);
        button.getStyleClass().add(styleClass);
        return button;
    }

    public void clear1(){
        Player player = positionOneCB.getValue();
        if(!positionTwoCB.getItems().contains(player)){
            positionTwoCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionTenCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionOneCB.setValue(null);
        p1.setText("*");
        updateStatLabels();
    }

    public void clear2(){
        Player player = positionTwoCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionTenCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionTwoCB.setValue(null);
        p2.setText("*");
        updateStatLabels();
    }

    public void clear3(){
        Player player = positionThreeCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionTwoCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionTenCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionThreeCB.setValue(null);
        p3.setText("*");
        updateStatLabels();
    }

    public void clear4(){
        Player player = positionFourCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionTwoCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionTenCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionFourCB.setValue(null);
        p4.setText("*");
        updateStatLabels();
    }

    public void clear5(){
        Player player = positionFiveCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionTwoCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionTenCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionFiveCB.setValue(null);
        p5.setText("*");
        updateStatLabels();
    }

    public void clear6(){
        Player player = positionSixCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionTwoCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionTenCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionSixCB.setValue(null);
        p6.setText("*");
        updateStatLabels();
    }

    public void clear7(){
        Player player = positionSevenCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionTwoCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionTenCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionSevenCB.setValue(null);
        p7.setText("*");
        updateStatLabels();
    }

    public void clear8(){
        Player player = positionEightCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionTwoCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionTenCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionEightCB.setValue(null);
        p8.setText("*");
        updateStatLabels();
    }

    public void clear9(){
        Player player = positionNineCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionTwoCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionTenCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionNineCB.setValue(null);
        p9.setText("*");
        updateStatLabels();
    }

    public void clear10(){
        Player player = positionTenCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionTwoCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionElevenCB.getItems().add(player);
        }
        positionTenCB.setValue(null);
        p10.setText("*");
        updateStatLabels();
    }

    public void clear11(){
        Player player = positionElevenCB.getValue();
        if(!positionOneCB.getItems().contains(player)) {
            positionOneCB.getItems().add(player);
            positionTwoCB.getItems().add(player);
            positionThreeCB.getItems().add(player);
            positionFourCB.getItems().add(player);
            positionFiveCB.getItems().add(player);
            positionSixCB.getItems().add(player);
            positionSevenCB.getItems().add(player);
            positionEightCB.getItems().add(player);
            positionNineCB.getItems().add(player);
            positionTenCB.getItems().add(player);
        }
        positionElevenCB.setValue(null);
        p11.setText("*");
        updateStatLabels();
    }

    //Clear the screen of everything
    public void clearScreen(){
        this.getChildren().clear();
    }

    public void setFormation442(){
        //Adds the formation and play style selectors
        this.add(formationLabel, 0, 1);
        this.add(formation, 1, 1);
        this.add(playStyleLabel, 0, 2);
        this.add(playStyle, 1, 2);

        //Add the labels to the screen
        this.addColumn(0, gkLabel, rbLabel, rcbLabel, lcbLabel, lbLabel, rmLabel, rcmLabel, lcmLabel, lmLabel, rstLabel, lstLabel);
        //Add the combo boxes to the screen
        this.addColumn(1,positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);

        //Add the *'s to the right side of the screen
        add(p1, 5, 10);
        add(p2, 7, 8);
        add(p3, 6, 8);
        add(p4, 4, 8);
        add(p5, 3, 8);
        add(p6, 7, 6);
        add(p7, 6, 6);
        add(p8, 4, 6);
        add(p9, 3, 6);
        add(p10, 6, 4);
        add(p11, 4, 4);

        //Adds the remove buttons
        addRemoveButtons();

        add(attackingScoreLabel, 9, 1);
        add(creativeScoreLabel, 9, 2);
        add(defensiveScoreLabel, 9, 3);
    }

    public void setFormation433(){
        //Adds the formation and play style selectors
        this.add(formationLabel, 0, 1);
        this.add(formation, 1, 1);
        this.add(playStyleLabel, 0, 2);
        this.add(playStyle, 1, 2);

        //Add the labels to the screen
        this.addColumn(0, gkLabel, rbLabel, rcbLabel, lcbLabel, lbLabel, rcmLabel, cmLabel, lcmLabel, rwLabel, stLabel, lwLabel);
        //Add the combo boxes to the screen
        this.addColumn(1,positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);

        //Add the *'s to the right side of the screen
        add(p1, 5, 10);
        add(p2, 7, 8);
        add(p3, 6, 8);
        add(p4, 4, 8);
        add(p5, 3, 8);
        add(p6, 6, 6);
        add(p7, 5, 6);
        add(p8, 4, 6);
        add(p9, 7, 4);
        add(p10, 5, 4);
        add(p11, 3, 4);

        //Adds the remove buttons
        addRemoveButtons();

        add(attackingScoreLabel, 8, 1);
        add(creativeScoreLabel, 8, 2);
        add(defensiveScoreLabel, 8, 3);
    }

    public void setFormation532(){
        //Adds the formation and play style selectors
        this.add(formationLabel, 0, 1);
        this.add(formation, 1, 1);
        this.add(playStyleLabel, 0, 2);
        this.add(playStyle, 1, 2);

        //Add the labels to the screen
        this.addColumn(0, gkLabel, rbLabel, rcbLabel, cbLabel, lcbLabel, lbLabel, rcmLabel, cmLabel, lcmLabel, rstLabel, lstLabel);
        //Add the combo boxes to the screen
        this.addColumn(1,positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);

        //Add the *'s to the right side of the screen
        add(p1, 5, 10);
        add(p2, 7, 8);
        add(p3, 6, 8);
        add(p4, 5, 8);
        add(p5, 4, 8);
        add(p6, 3, 8);
        add(p7, 6, 6);
        add(p8, 5, 6);
        add(p9, 4, 6);
        add(p10, 6, 4);
        add(p11, 4, 4);

        //Adds the remove buttons
        addRemoveButtons();

        add(attackingScoreLabel, 8, 1);
        add(creativeScoreLabel, 8, 2);
        add(defensiveScoreLabel, 8, 3);
    }

    public void setFormation343(){
        //Adds the formation and play style selectors
        this.add(formationLabel, 0, 1);
        this.add(formation, 1, 1);
        this.add(playStyleLabel, 0, 2);
        this.add(playStyle, 1, 2);

        //Add the labels to the screen
        this.addColumn(0, gkLabel, rcbLabel, cbLabel, lcbLabel, rmLabel, rcmLabel, lcmLabel, lmLabel, rwLabel, stLabel, lwLabel);
        //Add the combo boxes to the screen
        this.addColumn(1,positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB);

        //Add the *'s to the right side of the screen
        add(p1, 5, 10);
        add(p2, 6, 8);
        add(p3, 5, 8);
        add(p4, 4, 8);
        add(p5, 7, 6);
        add(p6, 6, 6);
        add(p7, 4, 6);
        add(p8, 3, 6);
        add(p9, 7, 4);
        add(p10, 5, 4);
        add(p11, 3, 4);

        //Adds the remove buttons
        addRemoveButtons();

        add(attackingScoreLabel, 8, 1);
        add(creativeScoreLabel, 8, 2);
        add(defensiveScoreLabel, 8, 3);
    }

    public void addRemoveButtons(){
        add(clearButton1, 2, 3);
        add(clearButton2, 2, 4);
        add(clearButton3, 2, 5);
        add(clearButton4, 2, 6);
        add(clearButton5, 2, 7);
        add(clearButton6, 2, 8);
        add(clearButton7, 2, 9);
        add(clearButton8, 2, 10);
        add(clearButton9, 2, 11);
        add(clearButton10, 2, 12);
        add(clearButton11, 2, 13);
    }

    public ObservableList<Player> getPlayersFromTeam(){
        ArrayList<Player> players = null;
        try {
            players = Player.readAllPlayers("WHERE teamCode='" + GameState.readTeam(GameState.readSaveName()) + "'");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        ObservableList<Player> playerList = FXCollections.observableArrayList(players);
        return playerList;
    }

    public void updatePlayerLabels(){
        p1.setText(positionOneCB.getValue().toString());
        p2.setText(positionTwoCB.getValue().toString());
        p3.setText(positionThreeCB.getValue().toString());
        p4.setText(positionFourCB.getValue().toString());
        p5.setText(positionFiveCB.getValue().toString());
        p6.setText(positionSixCB.getValue().toString());
        p7.setText(positionSevenCB.getValue().toString());
        p8.setText(positionEightCB.getValue().toString());
        p9.setText(positionNineCB.getValue().toString());
        p10.setText(positionTenCB.getValue().toString());
        p11.setText(positionElevenCB.getValue().toString());
    }

    public void updateStatLabels(){
        ArrayList<ComboBox> cbs = new ArrayList<>(List.of(positionOneCB, positionTwoCB, positionThreeCB, positionFourCB, positionFiveCB, positionSixCB, positionSevenCB, positionEightCB, positionNineCB, positionTenCB, positionElevenCB));
        attackingScore = 0.0;
        creativeScore = 0.0;
        defensiveScore = 0.0;
        for(ComboBox cb : cbs){
            if(cb.getValue() != null){
                Player player = (Player) cb.getValue();
                attackingScore += player.getAttackingStat();
                creativeScore += player.getCreativityStat();
                defensiveScore += player.getDefensiveStat();
            }
        }

        if(playStyle.getValue() != null){
            switch(playStyle.getValue()){
                case "Tiki Taka":
                    attackingScore = attackingScore * 0.7;
                    creativeScore = creativeScore * 1.5;
                    defensiveScore = defensiveScore * 1.2;
                    break;
                case "Gegenpress":
                    attackingScore = attackingScore * 1.2;
                    creativeScore = creativeScore * 1.1;
                    defensiveScore = defensiveScore * 0.8;
                    break;
                case "Counter Attack":
                    attackingScore = attackingScore * 0.9;
                    creativeScore = creativeScore * 0.6;
                    defensiveScore = defensiveScore * 1.6;
                    break;
                case "Park the bus":
                    attackingScore = attackingScore * 0.4;
                    creativeScore = creativeScore * 0.4;
                    defensiveScore = defensiveScore * 2.0;
                    break;
                default:
                    break;
            }
        }


        attackingScoreLabel.setText("Attacking Score: " + round(attackingScore, 2));
        creativeScoreLabel.setText("Creative Score: " + round(creativeScore, 2));
        defensiveScoreLabel.setText("Defensive Score: " + round(defensiveScore, 2));
    }

    //This method is used round the doubles to a specified number of decimal places
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    //This method is used to update options in the combo box
    public void updateCBOne(){
        Player player = (Player) positionOneCB.getValue();
        System.out.println(player);

        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer1 != null) {
            positionTwoCB.getItems().add(previousPlayer1);
            positionThreeCB.getItems().add(previousPlayer1);
            positionFourCB.getItems().add(previousPlayer1);
            positionFiveCB.getItems().add(previousPlayer1);
            positionSixCB.getItems().add(previousPlayer1);
            positionSevenCB.getItems().add(previousPlayer1);
            positionEightCB.getItems().add(previousPlayer1);
            positionNineCB.getItems().add(previousPlayer1);
            positionTenCB.getItems().add(previousPlayer1);
            positionElevenCB.getItems().add(previousPlayer1);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBTwo(){
        Player player = (Player) positionTwoCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer2 != null) {
            positionOneCB.getItems().add(previousPlayer2);
            positionThreeCB.getItems().add(previousPlayer2);
            positionFourCB.getItems().add(previousPlayer2);
            positionFiveCB.getItems().add(previousPlayer2);
            positionSixCB.getItems().add(previousPlayer2);
            positionSevenCB.getItems().add(previousPlayer2);
            positionEightCB.getItems().add(previousPlayer2);
            positionNineCB.getItems().add(previousPlayer2);
            positionTenCB.getItems().add(previousPlayer2);
            positionElevenCB.getItems().add(previousPlayer2);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBThree(){
        Player player = (Player) positionThreeCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer3 != null) {
            positionOneCB.getItems().add(previousPlayer3);
            positionTwoCB.getItems().add(previousPlayer3);
            positionFourCB.getItems().add(previousPlayer3);
            positionFiveCB.getItems().add(previousPlayer3);
            positionSixCB.getItems().add(previousPlayer3);
            positionSevenCB.getItems().add(previousPlayer3);
            positionEightCB.getItems().add(previousPlayer3);
            positionNineCB.getItems().add(previousPlayer3);
            positionTenCB.getItems().add(previousPlayer3);
            positionElevenCB.getItems().add(previousPlayer3);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBFour(){
        Player player = (Player) positionFourCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer4 != null) {
            positionOneCB.getItems().add(previousPlayer4);
            positionTwoCB.getItems().add(previousPlayer4);
            positionThreeCB.getItems().add(previousPlayer4);
            positionFiveCB.getItems().add(previousPlayer4);
            positionSixCB.getItems().add(previousPlayer4);
            positionSevenCB.getItems().add(previousPlayer4);
            positionEightCB.getItems().add(previousPlayer4);
            positionNineCB.getItems().add(previousPlayer4);
            positionTenCB.getItems().add(previousPlayer4);
            positionElevenCB.getItems().add(previousPlayer4);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBFive(){
        Player player = (Player) positionFiveCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer5 != null) {
            positionOneCB.getItems().add(previousPlayer5);
            positionTwoCB.getItems().add(previousPlayer5);
            positionThreeCB.getItems().add(previousPlayer5);
            positionFourCB.getItems().add(previousPlayer5);
            positionSixCB.getItems().add(previousPlayer5);
            positionSevenCB.getItems().add(previousPlayer5);
            positionEightCB.getItems().add(previousPlayer5);
            positionNineCB.getItems().add(previousPlayer5);
            positionTenCB.getItems().add(previousPlayer5);
            positionElevenCB.getItems().add(previousPlayer5);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBSix(){
        Player player = (Player) positionSixCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer6 != null) {
            positionOneCB.getItems().add(previousPlayer6);
            positionTwoCB.getItems().add(previousPlayer6);
            positionThreeCB.getItems().add(previousPlayer6);
            positionFourCB.getItems().add(previousPlayer6);
            positionFiveCB.getItems().add(previousPlayer6);
            positionSevenCB.getItems().add(previousPlayer6);
            positionEightCB.getItems().add(previousPlayer6);
            positionNineCB.getItems().add(previousPlayer6);
            positionTenCB.getItems().add(previousPlayer6);
            positionElevenCB.getItems().add(previousPlayer6);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBSeven(){
        Player player = (Player) positionSevenCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer7 != null) {
            positionOneCB.getItems().add(previousPlayer7);
            positionTwoCB.getItems().add(previousPlayer7);
            positionThreeCB.getItems().add(previousPlayer7);
            positionFourCB.getItems().add(previousPlayer7);
            positionFiveCB.getItems().add(previousPlayer7);
            positionSixCB.getItems().add(previousPlayer7);
            positionEightCB.getItems().add(previousPlayer7);
            positionNineCB.getItems().add(previousPlayer7);
            positionTenCB.getItems().add(previousPlayer7);
            positionElevenCB.getItems().add(previousPlayer7);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBEight(){
        Player player = (Player) positionEightCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer8 != null) {
            positionOneCB.getItems().add(previousPlayer8);
            positionTwoCB.getItems().add(previousPlayer8);
            positionThreeCB.getItems().add(previousPlayer8);
            positionFourCB.getItems().add(previousPlayer8);
            positionFiveCB.getItems().add(previousPlayer8);
            positionSixCB.getItems().add(previousPlayer8);
            positionSevenCB.getItems().add(previousPlayer8);
            positionNineCB.getItems().add(previousPlayer8);
            positionTenCB.getItems().add(previousPlayer8);
            positionElevenCB.getItems().add(previousPlayer8);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBNine(){
        Player player = (Player) positionNineCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer9 != null) {
            positionOneCB.getItems().add(previousPlayer9);
            positionTwoCB.getItems().add(previousPlayer9);
            positionThreeCB.getItems().add(previousPlayer9);
            positionFourCB.getItems().add(previousPlayer9);
            positionFiveCB.getItems().add(previousPlayer9);
            positionSixCB.getItems().add(previousPlayer9);
            positionSevenCB.getItems().add(previousPlayer9);
            positionEightCB.getItems().add(previousPlayer9);
            positionTenCB.getItems().add(previousPlayer9);
            positionElevenCB.getItems().add(previousPlayer9);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBTen(){
        Player player = (Player) positionTenCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionElevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer10 != null) {
            positionOneCB.getItems().add(previousPlayer10);
            positionTwoCB.getItems().add(previousPlayer10);
            positionThreeCB.getItems().add(previousPlayer10);
            positionFourCB.getItems().add(previousPlayer10);
            positionFiveCB.getItems().add(previousPlayer10);
            positionSixCB.getItems().add(previousPlayer10);
            positionSevenCB.getItems().add(previousPlayer10);
            positionEightCB.getItems().add(previousPlayer10);
            positionNineCB.getItems().add(previousPlayer10);
            positionElevenCB.getItems().add(previousPlayer10);
        }
    }

    //This method is used to update options in the combo box
    public void updateCBEleven(){
        Player player = (Player) positionElevenCB.getValue();
        System.out.println(player);

        positionOneCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTwoCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFourCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionFiveCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSixCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionSevenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionEightCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionNineCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionTenCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));
        positionThreeCB.getItems().removeIf( d -> (d.getSurname().equals(player.getSurname())));

        if(previousPlayer11 != null) {
            positionOneCB.getItems().add(previousPlayer11);
            positionTwoCB.getItems().add(previousPlayer11);
            positionThreeCB.getItems().add(previousPlayer11);
            positionFourCB.getItems().add(previousPlayer11);
            positionFiveCB.getItems().add(previousPlayer11);
            positionSixCB.getItems().add(previousPlayer11);
            positionSevenCB.getItems().add(previousPlayer11);
            positionEightCB.getItems().add(previousPlayer11);
            positionNineCB.getItems().add(previousPlayer11);
            positionTenCB.getItems().add(previousPlayer11);
        }
    }
}