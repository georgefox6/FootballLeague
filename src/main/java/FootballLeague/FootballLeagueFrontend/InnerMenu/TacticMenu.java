package FootballLeague.FootballLeagueFrontend.InnerMenu;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TacticMenu extends VBox {
    public Button newTacticButton;
    public Button loadTacticButton;
    public Button saveTacticButton;

    public TacticMenu(){
        newTacticButton = new Button("Reset Tactic");
        loadTacticButton = new Button("Load Tactic");
        saveTacticButton = new Button("Save Tactic");

        //Adds the content to the left menu Tactic
        this.getChildren().addAll(newTacticButton, saveTacticButton, loadTacticButton);
        this.setSpacing(10);
        this.setPadding(new Insets(15, 12, 15, 12));
    }
}
