package FootballLeague;/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import FootballLeague.FootballLeagueFrontend.GameMenu;
import FootballLeague.FootballLeagueFrontend.MainGame;
import FootballLeague.FootballLeagueFrontend.MainMenu;
import javafx.application.Application;

import FootballLeague.LogHandler.LogHandler;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static LogHandler log = new LogHandler("FootballLeague.App");

    public static void main(String[] args) {

        System.out.println(new App().getGreeting());

        log.log("This is a log");
        log.log("EXCEPTION", "There was an Exception");

        Application.launch(GameMenu.class, null);
    }
}
