package FootballLeague.FootballLeagueFrontend;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SaveBox {

    static String saveName;

    public static String display(String title, String message) {
        Stage window = new Stage();

        window.setTitle(title);
        window.setMaxWidth(200);

        //Makes it so you have to deal with the alert box before you can interact with the rest of the program
        window.initModality(Modality.APPLICATION_MODAL);

        //Creates the labels and button on the alert box
        Label label = new Label(message);
        TextField saveNameField = new TextField();
        Button saveButton = new Button("Save");

        saveButton.setOnAction(e -> {
            if(!saveNameField.getText().equals("")){
                window.close();
                saveName = saveNameField.getText();
            }
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, saveNameField, saveButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return saveName;
    }
}
