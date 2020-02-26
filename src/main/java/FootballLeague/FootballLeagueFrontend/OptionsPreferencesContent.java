package FootballLeague.FootballLeagueFrontend;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class OptionsPreferencesContent extends GridPane {
    Label resolutionLabel;
    ComboBox<String> resolutionCB;
    Label themeLabel;
    ComboBox<String> themeCB;;

    public OptionsPreferencesContent(){
        resolutionLabel = new Label("Resolution:");
        resolutionCB = new ComboBox();
        themeLabel = new Label("Theme:");
        themeCB = new ComboBox();

        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(0, 10, 0, 10));

        resolutionCB.getItems().addAll("800 x 400", "1020, 500", "1920 x 1080", "2560 x 1440");
        themeCB.getItems().addAll("Not Twitter", "Dark Theme", "Very Colourful");

        this.add(resolutionLabel, 2, 1);
        this.add(resolutionCB, 3, 1);
        this.add(themeLabel, 2, 2);
        this.add(themeCB, 3, 2);
    }
}
