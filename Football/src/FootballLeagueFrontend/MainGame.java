package FootballLeagueFrontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainGame extends Application {

    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Football League");
        //Replaces the default closing to instead close the program using our method
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        //////////////////////////////////////////////
        //                  TOP MENU                //
        //////////////////////////////////////////////
        //Creates the menu at the top of the window
        HBox topMenu = new HBox();
        Button homeButton = new Button("Home");
        Button teamButton = new Button("Team");
        Button leagueButton = new Button("League");
        Button scoutingButton = new Button("Scouting");
        Button tacticButton = new Button("Tactics");
        Button trainingButton = new Button("Training");
        Button clubButton = new Button("Club");
        Button quitButton = new Button("Quit");
        Label gameWeek = new Label("Game week 4");
        //Adds the buttons to the menu
        topMenu.getChildren().addAll(homeButton, teamButton, leagueButton, scoutingButton, gameWeek, tacticButton, trainingButton, clubButton, quitButton);
        //Sets the spacing for the top Menu
        topMenu.setSpacing(10);
        topMenu.setPadding(new Insets(15, 12, 15, 12));

        //////////////////////////////////////////
        //               LEFT MENU              //
        //////////////////////////////////////////

        //Creates the left menu home
        VBox leftMenuHome = new VBox();
        Button overviewButton = new Button("Overview");
        Button settingsButton = new Button("Settings");
        //Adds the content to the left menu Home
        leftMenuHome.getChildren().addAll(overviewButton, settingsButton);

        //Creates the left menu team
        VBox leftMenuTeam = new VBox();
        Button firstTeamButton = new Button("First Team");
        Button youthTeamButton = new Button("Youth Team");
        Button womenTeamButton = new Button("Womens Team");
        //Adds the content to the left menu team
        leftMenuTeam.getChildren().addAll(firstTeamButton, youthTeamButton, womenTeamButton);

        //Creates the left menu League
        VBox leftMenuLeague = new VBox();
        Button leagueTableButton = new Button("League Table");
        Button topScorersButton = new Button("Top Scorers");
        Button resultsButton = new Button("Results");
        Button fixturesButton = new Button("Fixtures");
        //Adds the content to the left menu League
        leftMenuLeague.getChildren().addAll(leagueTableButton, topScorersButton, resultsButton, fixturesButton);

        //creates the main layout and adds the topMenu main layout and the leftMenuHome as default
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenuHome);

        //////////////////////////////////////
        //      Button action listeners     //
        //////////////////////////////////////

        //Depending on the button press it will set the left menu
        homeButton.setOnAction(e -> {
            borderPane.setLeft(leftMenuHome);
        });
        teamButton.setOnAction(e -> {
            borderPane.setLeft(leftMenuTeam);
        });
        leagueButton.setOnAction(e -> {
            borderPane.setLeft(leftMenuLeague);
        });
        quitButton.setOnAction(e -> {
            closeProgram();
        });

        //Creates the scene with the borderPane layout window size
        Scene scene = new Scene(borderPane, 610, 500);
        scene.getStylesheets().add("FootballLeagueFrontend/FootballLeagueThemeOne.css");
        window.setScene(scene);
        window.show();
    }
    public void closeProgram(){
        Boolean answer = ConfirmBox.display("Quit?", "Are you sure you want to close the game?");
        if(answer)
            window.close();

    }
}
