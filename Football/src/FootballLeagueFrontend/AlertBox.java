package FootballLeagueFrontend;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        window.setTitle(title);
        window.setMaxWidth(200);

        //Makes it so you have to deal with the alert box before you can interact with the rest of the program
        window.initModality(Modality.APPLICATION_MODAL);

        //Creates the labels and button on the alert box
        Label label = new Label(message);
        Button okButton = new Button("OK");

        okButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, okButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

}
