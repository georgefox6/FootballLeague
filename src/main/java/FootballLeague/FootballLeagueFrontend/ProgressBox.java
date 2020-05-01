package FootballLeague.FootballLeagueFrontend;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressBox extends Stage {

    ProgressBar progressBar;
    VBox vbox;

    public ProgressBox(String string){

        //This label will display what process the progress bar is linked to
        Label label = new Label(string);

        //Create a new progress bar and set the initial progress to 0
        progressBar = new ProgressBar();
        progressBar.setProgress(0);

        vbox = new VBox();

        vbox.getChildren().add(label);
        vbox.getChildren().add(progressBar);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(vbox);

        Scene scene = new Scene(borderPane, 280, 100);

        //Makes it so you have to deal with the alert box before you can interact with the rest of the program
        this.initModality(Modality.APPLICATION_MODAL);

        this.setScene(scene);

        this.show();
    }
}
