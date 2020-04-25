package FootballLeague.FootballLeagueFrontend;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateDbVbox extends VBox {

    ToggleGroup group;
    RadioButton small;
    RadioButton medium;
    RadioButton large;
    Button backButton;
    Button generateButton;

    public CreateDbVbox(){
        Label title = new Label("Create New Database");
        Label dbSize = new Label("Database Size:");

        group = new ToggleGroup();

        small = new RadioButton("Small - Premier League");
        small.setToggleGroup(group);
        small.setSelected(true);

        medium = new RadioButton("Medium - Premier League, Championship");
        medium.setToggleGroup(group);

        large = new RadioButton("Large - Premier League, Championship, \n Bundesliga, Series A, \n La Liga, Ligue 1");
        large.setToggleGroup(group);

        backButton = new Button("Back");
        generateButton = new Button("Generate");

        this.setSpacing(10);
        this.setPadding(new Insets(25));

        HBox buttons = new HBox();

        buttons.getChildren().addAll(backButton, generateButton);

        this.getChildren().add(title);
        this.getChildren().add(dbSize);
        this.getChildren().add(small);
        this.getChildren().add(medium);
        this.getChildren().add(large);
        this.getChildren().add(buttons);

    }
}
