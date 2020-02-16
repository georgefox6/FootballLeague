package FootballLeagueFrontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TrainingMenu extends VBox {
    Button abcButton;

    public TrainingMenu(){
        abcButton = new Button("ABC");

        //Adds the content to the left menu Training
        this.getChildren().addAll(abcButton);
        this.setSpacing(10);
        this.setPadding(new Insets(15, 12, 15, 12));
    }
}
