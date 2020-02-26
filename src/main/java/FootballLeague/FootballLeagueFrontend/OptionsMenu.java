package FootballLeague.FootballLeagueFrontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class OptionsMenu extends VBox {
    Button optionsOptionsButton;
    Button saveGameOptionsButton;
    Button loadGameOptionsButton;
    Button quitButton;

    public OptionsMenu(){
        optionsOptionsButton = new Button("Options");
        saveGameOptionsButton = new Button("Save Game");
        loadGameOptionsButton = new Button("Load Game");
        quitButton = new Button("Quit Game");

        //Adds the content to the left menu Options
        this.getChildren().addAll(optionsOptionsButton, saveGameOptionsButton, loadGameOptionsButton, quitButton);
        this.setSpacing(10);
        this.setPadding(new Insets(15, 12, 15, 12));
    }
}
