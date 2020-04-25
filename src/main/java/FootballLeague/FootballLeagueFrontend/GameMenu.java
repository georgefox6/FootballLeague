package FootballLeague.FootballLeagueFrontend;

import java.io.File.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Team;
import FootballLeague.FootballLeagueBackend.WebScraper.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;

import FootballLeague.FootballLeagueBackend.FileHandler;

import javafx.util.Callback;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static FootballLeague.FootballLeagueFrontend.MainGame.initNewGame;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GameMenu extends Application {

    public static Logger logger = LogManager.getLogger("com.josh");

    ComboBox<Team> teamSelector;
    ComboBox<String> baseGameSelector;
    ProgressBox progressBox;
    CreateDbVbox createDbVbox;
    Button createGameButton;
    Stage stage;

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    private void initUI(Stage stage) {

        /////////////////////////////
        //      Home Screen        //
        /////////////////////////////

        Button newGameButton = new Button();
        Button loadGameButton = new Button();
        Button settingsButton = new Button();
        Button quitButton = new Button();

        newGameButton.setText("New game");
        loadGameButton.setText("Load game");
        settingsButton.setText("Settings");
        quitButton.setText("Quit");

        VBox mainMenu = new VBox();
        mainMenu.getChildren().addAll(newGameButton, loadGameButton, settingsButton, quitButton);
        mainMenu.setSpacing(10);
        mainMenu.setPadding(new Insets(25));

        BorderPane borderPaneMainMenu = new BorderPane();
        borderPaneMainMenu.setCenter(mainMenu);

        Scene mainMenuScene = new Scene(borderPaneMainMenu, 280, 200);
        mainMenuScene.getStylesheets().add("FootballLeague/src/main/Stylesheets/NotTwitter.css");

        /////////////////////////////
        //     NewGame Screen      //
        /////////////////////////////

        baseGameSelector = new ComboBox<>();

        TextField newGameName = new TextField();
        newGameName.setDisable(true);

        Button backButton = new Button();

        createGameButton = new Button();
        createGameButton.setDisable(true);

        teamSelector = new ComboBox<>();
        teamSelector.setDisable(true);


        backButton.setText("Back");
        createGameButton.setText("Create game");

        HBox newGameMenuButtons = new HBox();
        newGameMenuButtons.getChildren().addAll(backButton, createGameButton);
        newGameMenuButtons.setSpacing(10);

        VBox newGameMenu = new VBox();
        newGameMenu.getChildren().addAll(baseGameSelector, newGameName, teamSelector, newGameMenuButtons);
        newGameMenu.setSpacing(10);
        newGameMenu.setPadding(new Insets(25));

        BorderPane borderPaneNewGameScreen = new BorderPane();
        borderPaneNewGameScreen.setCenter(newGameMenu);

        Scene newGameScreenScene = new Scene(borderPaneNewGameScreen, 280, 200);
        newGameScreenScene.getStylesheets().add("/src/main/Stylesheets/NotTwitter.css");

        /////////////////////////////
        //    LoadGame Screen      //
        /////////////////////////////

        ObservableList<String> saveList = getAllSaves();
        ComboBox<String> saveGameSelector = new ComboBox<>(saveList);

        Button loadBackButton = new Button();
        loadBackButton.setText("Back");

        Button loadLoadButton = new Button();
        loadLoadButton.setText("Load Game");

        HBox loadGameMenuButtons = new HBox();
        loadGameMenuButtons.getChildren().addAll(loadBackButton, loadLoadButton);
        loadGameMenuButtons.setSpacing(10);

        VBox loadGameMenu = new VBox();
        loadGameMenu.getChildren().addAll(saveGameSelector, loadGameMenuButtons);
        loadGameMenu.setSpacing(10);
        loadGameMenu.setPadding(new Insets(25));

        BorderPane borderPaneLoadGameScreen = new BorderPane();
        borderPaneLoadGameScreen.setCenter(loadGameMenu);

        Scene loadGameScreenScene = new Scene(borderPaneLoadGameScreen, 280, 200);
        loadGameScreenScene.getStylesheets().add("/src/main/Stylesheets/NotTwitter.css");


        ////////////////////////////
        //    Settings Screen     //
        ////////////////////////////

        Button settingsCreateNewDBButton = new Button();
        settingsCreateNewDBButton.setText("Create DB");

        Button settingsBackButton = new Button();
        settingsBackButton.setText("Back");

        VBox settingsMenu = new VBox();
        settingsMenu.getChildren().addAll(settingsCreateNewDBButton, settingsBackButton);
        settingsMenu.setSpacing(10);
        settingsMenu.setPadding(new Insets(25));

        BorderPane borderPaneSettingsScreen = new BorderPane();
        borderPaneSettingsScreen.setCenter(settingsMenu);

        Scene settingsScene = new Scene(borderPaneSettingsScreen, 280, 200);

        /////////////////////////////
        //   Home Screen Actions   //
        /////////////////////////////

        newGameButton.setOnAction((ActionEvent event) -> {
            ObservableList<String> baseGameList = getAllBaseGames();
            baseGameSelector.setItems(baseGameList);
            pressedNewGameButton(stage, newGameScreenScene);
        });
        loadGameButton.setOnAction((ActionEvent event) -> {
            pressedLoadGameButton(stage, loadGameScreenScene);
        });
        settingsButton.setOnAction((ActionEvent event) -> {
            pressedSettingsButton(stage, settingsScene);
        });
        quitButton.setOnAction((ActionEvent event) -> {
            pressedQuitButton();
        });

        /////////////////////////////
        // New Game Screen Actions //
        /////////////////////////////

        backButton.setOnAction((ActionEvent event) -> {
            pressedBackButton(stage, mainMenuScene);
        });
        createGameButton.setOnAction((ActionEvent event) -> {
            pressedCreateGameButton(baseGameSelector.getValue(), newGameName.getText());
        });

        baseGameSelector.setOnAction(e -> {
            newGameName.setDisable(false);
            createGameButton.setDisable(false);
            teamSelector.setDisable(false);

            try {
                GameState.writeSaveName("../BaseGames/" + baseGameSelector.getValue());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            ObservableList<Team> teamList = getAllTeams();
            teamSelector.setItems(teamList);
        });

        //////////////////////////////
        // Load Game Screen Actions //
        //////////////////////////////

        loadBackButton.setOnAction((ActionEvent event) -> {
            pressedBackButton(stage, mainMenuScene);
        });

        loadLoadButton.setOnAction((ActionEvent event) -> {
            String saveName = saveGameSelector.getValue();
            loadGame(saveName);
        });

        ///////////////////////////////
        //  Settings Screen Actions  //
        ///////////////////////////////

        settingsBackButton.setOnAction(e -> {
            pressedBackButton(stage, mainMenuScene);
        });

        createDbVbox = new CreateDbVbox();
        settingsCreateNewDBButton.setOnAction(e -> {
            BorderPane borderPaneNewDB = new BorderPane();
            borderPaneNewDB.setCenter(createDbVbox);
            Scene scene = new Scene(borderPaneNewDB, 280, 200);
            stage.setScene(scene);
        });


        ///////////////////////////////
        //   Create new DB Actions   //
        ///////////////////////////////

        createDbVbox.generateButton.setOnAction(e -> {
            System.out.println(((RadioButton)createDbVbox.group.getSelectedToggle()).getText());
            String dbSize = ((RadioButton)createDbVbox.group.getSelectedToggle()).getText().split("-")[0];
            progressBox = new ProgressBox("Generating New " + dbSize + "Database");

            Task task = returnTaskJsonCreation(dbSize);

            progressBox.progressBar.progressProperty().bind(task.progressProperty());

            //Code to be run once the task has been completed successfully
            task.setOnSucceeded(f -> {
                progressBox.vbox.getChildren().add(new Label("Database Complete!"));

                Button okButton = new Button("Ok");
                progressBox.vbox.getChildren().add(okButton);

                okButton.setOnAction(g -> {
                    System.out.println("close");
                    progressBox.close();
                });

                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = myDateObj.format(myFormatObj);

                String baseGameName = dbSize.trim() + "-" + formattedDate;

                try {
                    Path source = Paths.get("src/main/resources/blank.db");
                    Path target = Paths.get("src/main/resources/BaseGames/" + baseGameName + ".db");
                    Files.copy(source, target, REPLACE_EXISTING);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                DatabasePopulator.jsonToDB("playerJsonSmall230420202026.json", baseGameName);

            });

            Thread thread = new Thread(task);
            thread.start();


        });

        createDbVbox.backButton.setOnAction(e -> {
            pressedBackButton(stage, settingsScene);
        });


        /////////////////////////////
        //    Initialize Stage     //
        /////////////////////////////

        stage.setTitle("Main menu");
        stage.setScene(mainMenuScene);
        stage.setMaxHeight(230);
        stage.setMaxWidth(260);
        stage.setResizable(false);
        stage.show();
    }
    private void pressedNewGameButton(Stage stage, Scene newGameScreenScene) {
        stage.setTitle("New game");
        stage.setScene(newGameScreenScene);
    }

    private void pressedLoadGameButton(Stage stage, Scene loadGameScreenScene) {
        stage.setTitle("Load Game");
        stage.setScene(loadGameScreenScene);
    }

    private void pressedSettingsButton(Stage stage, Scene scene) {
        stage.setTitle("Settings");
        stage.setScene(scene);
    }

    private void pressedQuitButton() {
        // stage.close();
    }

    private void pressedCreateGameButton(String baseGame, String saveGameName) {
        logger.info("Create game");
        if (checkGameExists(saveGameName)) {
            Boolean answer = ConfirmBox.display("Game already exists:", "Are you sure you want to overwrite this game?");
            if (answer) {
                try {
                    createNewGame(baseGame, saveGameName, true);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                createNewGame(baseGame, saveGameName, false);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void pressedBackButton(Stage stage, Scene mainMenuScene) {
        logger.info("Back");
        stage.setTitle("Main menu");
        stage.setScene(mainMenuScene);
    }

    private boolean checkGameExists(String saveGameName) {
        FileHandler f = new FileHandler();
        ArrayList<String> saveGameNames = f.getSaveGameNames();
        boolean gameExists = saveGameNames.contains(saveGameName);
        return gameExists;
    }

    private void createNewGame(String baseGame, String saveGameName, Boolean overwrite) throws IOException, InterruptedException {
        FileHandler f = new FileHandler();
        try {
            f.copyBaseSaveGame(baseGame, saveGameName, overwrite);
        } catch(IOException e) {
            e.printStackTrace();
        }
        //This will create the JSON file to keep track of the game state such as game week and team
        GameState.initGameState(teamSelector.getValue().getTeamCode(), saveGameName);

        //Used to store the save name
        GameState.writeSaveName(saveGameName);

        //Initialises the new game: sets up the league tables and schedules all matches for the year
        initNewGame();

        //Once the save has been created, an instance of the main game will be created
        new MainGame();
    }

    public ObservableList<Team> getAllTeams(){
        ArrayList<Team> teams = Team.readAllTeams("");
        return FXCollections.observableArrayList(teams);
    }

    public ObservableList<String> getAllSaves(){
        FileHandler f = new FileHandler();
        return FXCollections.observableArrayList(f.getSaveGameNamesDB());
    }

    public ObservableList<String> getAllBaseGames(){
        FileHandler handler = new FileHandler();
        return FXCollections.observableArrayList(handler.getBaseGames());
    }

    private void loadGame(String saveGameName){
        try {
            GameState.writeSaveName(saveGameName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new MainGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

    //~~~~~~~~ Methods used for web scraping ~~~~~~~~~~~//

    public static ArrayList<String> getPlayerLinks(String teamUrl){
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        List<HtmlElement> playerRows = new ArrayList<>();
        try{
            HtmlPage page = client.getPage(teamUrl);
            playerRows = page.getByXPath("//*[@id=\"yw1\"]/table/tbody/tr");
        } catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<String> playerLinks = new ArrayList<>();
        if(playerRows.isEmpty()){
            System.out.println("No Results");
        } else {
            for(HtmlElement row : playerRows){
                String name = ((HtmlElement)row.getFirstByXPath("./td/table/tbody/tr/td/div/span/a")).asText();
                String profileHtml = ((HtmlAnchor)row.getFirstByXPath("./td/table/tbody/tr/td/div/span/a")).getHrefAttribute();
                playerLinks.add(profileHtml);
            }
        }
        return playerLinks;
    }

    public static ArrayList<String> getTeamLinks(String leagueUrl){
        System.out.println("Starting to get team links");
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        ArrayList<String> teamLinks = new ArrayList<>();

        try{
            HtmlPage page = client.getPage(leagueUrl);
            for(int i=1; i<30; i++){
                if((page.getFirstByXPath("//*[@id=\"yw1\"]/table/tbody/tr[" + i + "]/td[2]/a")) == null){
                    i = 50;
                } else {
                    String link = ((HtmlAnchor) page.getFirstByXPath("//*[@id=\"yw1\"]/table/tbody/tr[" + i + "]/td[2]/a")).getHrefAttribute();
                    teamLinks.add(link);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Finished getting the team links");
        return teamLinks;
    }


    public static PlayerJsonScraper getPlayer(String playerUrl) {
        PlayerJsonScraper player = new PlayerJsonScraper();

        try{
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            HtmlPage page = client.getPage(playerUrl);

            String dateOfBirth = null;
            int age = 0;
            String position = null;
            String nationality = null;
            String team = null;

            //Iterate over the table headers
            for(int i=1; i < 20; i++){
                HtmlElement tableData = null;
                HtmlElement tableHeader = null;
                if((page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/td")) == null){
                    tableData = (page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[1]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/td"));
                    tableHeader=(page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[1]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/th"));
                } else {
                    tableData = (page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/td"));
                    tableHeader=(page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/th"));
                }
                switch(tableHeader.asText()){
                    case "Date of birth:":
                        dateOfBirth = tableData.asText();
                        break;
                    case "Age:":
                        age = Integer.parseInt(tableData.asText());
                        break;
                    case "Citizenship:":
                        nationality = ((HtmlImage) tableData.getFirstByXPath("//td/img")).getAltAttribute();
                        break;
                    case "Position:":
                        position = tableData.asText();
                        break;
                    case "Current club:":
                        team = ((HtmlElement) tableData.getFirstByXPath("//td/a[2]")).asText();;
                        i = 21;
                        break;
                }
            }

            String name;
            if((page.getFirstByXPath("//*[@id=\"main\"]/div[8]/div/div/div[1]/div/div/h1")) == null){
                name = ((HtmlElement) page.getFirstByXPath("//*[@id=\"main\"]/div[8]/div/div/div[2]/div/div/h1")).asText();
            } else {
                name = ((HtmlElement) page.getFirstByXPath("//*[@id=\"main\"]/div[8]/div/div/div[1]/div/div/h1")).asText();
            }

            long value = 0L;
            if(page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[1]/div/div[4]/div[1]/div[1]/div/div[1]/div[2]/a[1]") != null){
                value = formatValue(((HtmlElement) page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[1]/div/div[4]/div[1]/div[1]/div/div[1]/div[2]/a[1]")).asText().replace(".",""));
            }
            if(page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[1]/div[2]/div[1]/div/div[4]/div[1]/div[1]/div/div[1]/div[2]/a[1]") != null){
                value = formatValue(((HtmlElement) page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[1]/div[2]/div[1]/div/div[4]/div[1]/div[1]/div/div[1]/div[2]/a[1]")).asText().replace(".",""));
            }

            String yw = null;
            assert position != null;
            if(page.getFirstByXPath("//*[@id=\"yw2\"]/table/tfoot/tr/td[5]") != null){
                yw = "yw2";
            } else if(page.getFirstByXPath("//*[@id=\"yw1\"]/table/tfoot/tr/td[5]") != null){
                yw = "yw1";
            }

            int gamesPlayed = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[3]")).asText());

            if(position.equals("Goalkeeper") && yw != null){
                int cleanSheets = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[5]")).asText());
                int goalsConceded = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[4]")).asText());
                player = new PlayerJsonScraper(name, dateOfBirth, age, position, nationality, team, gamesPlayed, cleanSheets, goalsConceded, value, playerUrl);
            } else if(yw != null) {
                int goalsScored = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[4]")).asText());
                int assists = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[5]")).asText());
                player = new PlayerJsonScraper("", name, dateOfBirth, age, position, nationality, team, gamesPlayed, goalsScored, assists, value, playerUrl);
            } else {
                player = new PlayerJsonScraper(name, dateOfBirth, age, position, nationality, team, gamesPlayed, 0, 0, 0, 0, value, playerUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Completed - " + player.name);
        return player;
    }

    public static int stringToInt(String str){
        if(str.equals("-")){
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }

    public static long formatValue(String str){
        str = str.substring(1);
        if(str.contains("m")){
            str = str.replace("m","");
            return Long.parseLong(str) * 1000000 / 100;
        }
        if(str.contains("k")){
            str = str.replace("k","");
            return Long.parseLong(str) * 1000;
        }
        return Long.parseLong(str);
    }

    public Task returnTaskJsonCreation(String dbSize){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                ArrayList<String> teamLinks = new ArrayList<>();
                ArrayList<PlayerJsonScraper> playerList = new ArrayList<>();
                System.out.println("Start Json Creation");
                String baseUrl = "https://www.transfermarkt.co.uk";
                String premierLeagueUrl = "https://www.transfermarkt.co.uk/premier-league/startseite/wettbewerb/GB1";
                String championshipUrl = "https://www.transfermarkt.co.uk/championship/startseite/wettbewerb/GB2/saison_id/2019";
                String bundesligaUrl = "https://www.transfermarkt.co.uk/1-bundesliga/startseite/wettbewerb/L1";
                String laLigaUrl = "https://www.transfermarkt.co.uk/primera-division/startseite/wettbewerb/ES1";
                String serieAUrl = "https://www.transfermarkt.co.uk/serie-a/startseite/wettbewerb/IT1";
                String ligue1Url = "https://www.transfermarkt.co.uk/ligue-1/startseite/wettbewerb/FR1";

                if(dbSize.equals("Small ")){
                    //Takes ~17 minutes 48 seconds to complete
                    teamLinks = getTeamLinks(premierLeagueUrl);
                } else if(dbSize.equals("Medium ")){
                    //Takes ~37 minutes 5 seconds to complete
                    teamLinks = getTeamLinks(championshipUrl);
                    teamLinks.addAll(getTeamLinks(premierLeagueUrl));
                } else if(dbSize.equals("Large ")){
                    //Takes ~1 hour 20 minutes 10 seconds to complete
                    teamLinks = getTeamLinks(premierLeagueUrl);
                    teamLinks.addAll(getTeamLinks(championshipUrl));
                    teamLinks.addAll(getTeamLinks(bundesligaUrl));
                    teamLinks.addAll(getTeamLinks(laLigaUrl));
                    teamLinks.addAll(getTeamLinks(serieAUrl));
                    teamLinks.addAll(getTeamLinks(ligue1Url));
                }
                int updateProgress = 1;
                for (String teamLink : teamLinks) {
                    if(isCancelled()){
                        System.out.println("Is cancelled");
                        break;
                    }
                    for (String playerLink : getPlayerLinks(baseUrl + teamLink)) {
                        //These players have insufficient data to use to will be skipped
                        if( playerLink.equals("/ahmadou-dia/profil/spieler/611673") ||
                                playerLink.equals("/callum-pearson/profil/spieler/547036") ||
                                playerLink.equals("/eiji-kawashima/profil/spieler/77383")  ||
                                playerLink.equals("/anthony-rouault/profil/spieler/690799")  ||
                                playerLink.equals("/boris-essele/profil/spieler/694499")  ||
                                playerLink.equals("/darell-tokpa/profil/spieler/655414")  ||
                                playerLink.equals("/louis-pelletier/profil/spieler/616283")) {

                        }
                        else {
                            playerList.add(getPlayer(baseUrl + playerLink));
                            System.out.println(playerLink);
                        }
                    }
                    //Updates the progress value of the task which is linked to the progress bar
                    updateProgress++;
                    updateProgress(updateProgress, teamLinks.size());
                }
                try {
                    //Writes the players to the json file
                    JsonScraperOperations.writePlayer(playerList, dbSize);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

}