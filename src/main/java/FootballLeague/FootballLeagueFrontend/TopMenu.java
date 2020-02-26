package FootballLeague.FootballLeagueFrontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TopMenu extends HBox {
    //Declaring elements of TopMenu
    Button teamButton;
    Button leagueButton;
    Button tacticButton;
    Button advanceButton;
    Button scoutingButton;
    Button trainingButton;
    Button clubButton;
    Button optionsButton;
    Label gameWeek;

    //Constructor
    public TopMenu(){
        teamButton = new Button("Team");
        leagueButton = new Button("League");
        tacticButton = new Button("Tactics");
        advanceButton = new Button("Advance");
        scoutingButton = new Button("Scouting");
        trainingButton = new Button("Training");
        clubButton = new Button("Club");
        optionsButton = new Button("Options");
        gameWeek = new Label("Game Week 41");

        //Adds the buttons to the menu
        this.getChildren().addAll(teamButton, leagueButton, tacticButton,  scoutingButton, gameWeek, trainingButton, clubButton, optionsButton, advanceButton);
        //Sets the spacing for the top Menu
        this.setSpacing(10);
        this.setPadding(new Insets(15, 12, 15, 12));
    }
}
