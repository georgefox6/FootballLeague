package FootballLeagueFrontend;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class OptionsPreferencesContent extends GridPane {
    Label resolutionLabel;
    ComboBox resolutionCB;
    Label themeLabel;
    ComboBox themeCB;;

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

        resolutionCB.setOnAction(e -> setResolution(resolutionCB.getValue().toString()));
        themeCB.setOnAction(e -> setTheme(themeCB.getValue().toString()));

        this.add(resolutionLabel, 2, 1);
        this.add(resolutionCB, 3, 1);
        this.add(themeLabel, 2, 2);
        this.add(themeCB, 3, 2);
    }

    public void setResolution(String res){
        //TODO Write function to change resolution
        System.out.println("Changed the resolution to " + res);
    }

    public void setTheme(String selectedTheme){
        //TODO Write function to change the theme (change the CSS)
        System.out.println("Changed the theme to " + selectedTheme);
    }
}
