package FootballLeague.FootballLeagueFrontend.InnerMenu;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LeagueMenu extends VBox {
    //Declaring elements of League Menu
    Button leagueTableButton;
    Button topScorersButton;
    Button resultsButton;
    Button fixturesButton;

    //Constructor
    public LeagueMenu(){
        leagueTableButton = new Button("League Table");
        topScorersButton = new Button("Top Scorers");
        resultsButton = new Button("Results");
        fixturesButton = new Button("Fixtures");

        //Adds the content to the left menu League
        this.getChildren().addAll(leagueTableButton, topScorersButton, resultsButton, fixturesButton);
        this.setSpacing(10);
        this.setPadding(new Insets(15, 12, 15, 12));
    }
}
