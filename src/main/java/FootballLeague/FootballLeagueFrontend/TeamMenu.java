package FootballLeague.FootballLeagueFrontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TeamMenu extends VBox {
    //Declaring elements of Team Menu
    Button firstTeamButton;
    Button youthTeamButton;
    Button womenTeamButton;

    //Constructor
    public TeamMenu(){
        firstTeamButton = new Button("First Team");
        youthTeamButton = new Button("Youth Team");
        womenTeamButton = new Button("Women's Team");

        //Adds the content to the left menu team
        this.getChildren().addAll(firstTeamButton, youthTeamButton, womenTeamButton);
        this.setSpacing(10);
        this.setPadding(new Insets(15, 12, 15, 12));
    }
}
