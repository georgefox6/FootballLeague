package FootballLeagueFrontend;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static Boolean answer;

    public static Boolean display(String title, String message) {
        Stage window = new Stage();

        window.setTitle(title);
        window.setMaxWidth(200);

        //Makes it so you have to deal with the alert box before you can interact with the rest of the program
        window.initModality(Modality.APPLICATION_MODAL);

        //Creates the labels and button on the alert box
        Label label = new Label(message);
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        //Action listeners for the buttons
        yesButton.setOnAction(e ->{
            answer = true;
            window.close();
        });
        noButton.setOnAction(e ->{
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
