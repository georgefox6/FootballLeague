package FootballLeague.FootballLeagueFrontend.InnerMenu;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ScoutingMenu extends VBox {
    Button viewPlayersButton;
    Button shortlistButton;
    Button scoutsButton;

    public ScoutingMenu(){
        viewPlayersButton = new Button("View Players");
        shortlistButton = new Button("Shortlist");
        scoutsButton = new Button("Scouts");

        //Adds the content to the left menu Scouting
        this.getChildren().addAll(viewPlayersButton, shortlistButton, scoutsButton);
        this.setSpacing(10);
        this.setPadding(new Insets(15, 12, 15, 12));
    }
}
