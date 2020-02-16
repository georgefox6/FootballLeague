package FootballLeagueFrontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ClubMenu extends VBox {
    Button facilitiesButton;
    Button historyButton;
    Button staffButton;
    Button financesButton;

    public ClubMenu(){
        facilitiesButton = new Button("Facilities");
        historyButton = new Button("History");
        staffButton = new Button("Staff");
        financesButton = new Button("Finances");

        //Adds the content to the left menu Club
        this.getChildren().addAll(facilitiesButton, historyButton, staffButton, financesButton);
        this.setSpacing(10);
        this.setPadding(new Insets(15, 12, 15, 12));
    }
}
