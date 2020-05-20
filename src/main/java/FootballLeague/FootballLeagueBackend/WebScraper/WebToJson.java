package FootballLeague.FootballLeagueBackend.WebScraper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebToJson {

    //This method is only used to write the json to store the current save name
    public static void writeObjects(ArrayList<TeamJsonScraper> teams, ArrayList<PlayerJsonScraper> players, String jsonFileName) throws IOException {

        //Add players to list
        JSONArray objectList = new JSONArray();

        //Iterate over each team and add the object to the objectList array
        for (TeamJsonScraper team : teams) {
            JSONObject teamDetails = new JSONObject();
            teamDetails.put("name", team.teamName);
            teamDetails.put("venueName", team.venueName);
            teamDetails.put("venueCapacity", String.valueOf(team.venueCapacity));
            teamDetails.put("league", team.league);

            JSONObject teamObject = new JSONObject();
            teamObject.put("team", teamDetails);

            objectList.add(teamObject);
        }

        //Iterate over each player and add the object to the objectList array
        for (PlayerJsonScraper player : players) {
            JSONObject playerDetails = new JSONObject();
            playerDetails.put("name", player.name);
            playerDetails.put("dateOfBirth", player.dateOfBirth);
            playerDetails.put("age", String.valueOf(player.age));
            playerDetails.put("position", player.position);
            playerDetails.put("nationality", player.nationality);
            playerDetails.put("team", player.team);
            playerDetails.put("gamesPlayed", String.valueOf(player.gamesPlayed));
            playerDetails.put("goalsScored", String.valueOf(player.goalsScored));
            playerDetails.put("assists", String.valueOf(player.assists));
            playerDetails.put("cleanSheets", String.valueOf(player.cleanSheets));
            playerDetails.put("goalsConceded", String.valueOf(player.goalsConceded));
            playerDetails.put("value", String.valueOf(player.value));
            playerDetails.put("playerUrl", player.playerUrl);

            JSONObject playerObject = new JSONObject();
            playerObject.put("player", playerDetails);

            objectList.add(playerObject);
        }

        //Sets the path where the JSON will be written
        Path filePath = Paths.get("src/main/resources/BaseGames/" + jsonFileName + ".json");

        //Check if the file exists
        if (!Files.exists(filePath)) {
            //If file doesn't exist the create new file
            Files.createFile(filePath);
        }

        //Write the arraylist of objects to the file
        Files.write(filePath, objectList.toJSONString().getBytes());
    }

    //This method is used to return an arraylist of team URLs when you provide a league URL
    public static ArrayList<String> getTeamLinks(String leagueUrl) {
        System.out.println("Starting to get team links");

        //Creates a new WebClient to scrape the page
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        ArrayList<String> teamLinks = new ArrayList<>();

        try {
            HtmlPage page = client.getPage(leagueUrl);
            //Iterate over the table rows to get the team link
            for (int i = 1; i < 30; i++) {
                if ((page.getFirstByXPath("//*[@id=\"yw1\"]/table/tbody/tr[" + i + "]/td[2]/a")) == null) {
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

    //This method is used to return an arraylist of player URLs when you provide a team URL
    public static ArrayList<String> getPlayerLinks(String teamUrl) {

        //Creates a new WebClient to scrape the page
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        List<HtmlElement> playerRows = new ArrayList<>();

        try {
            HtmlPage page = client.getPage(teamUrl);
            playerRows = page.getByXPath("//*[@id=\"yw1\"]/table/tbody/tr");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> playerLinks = new ArrayList<>();
        if (playerRows.isEmpty()) {
            System.out.println("No Results");
        } else {
            //For each row containing player data, retrieve the anchor link and add to the playerLinks arraylist
            for (HtmlElement row : playerRows) {
                String profileHtml = ((HtmlAnchor) row.getFirstByXPath("./td/table/tbody/tr/td/div/span/a")).getHrefAttribute();
                playerLinks.add(profileHtml);
            }
        }
        return playerLinks;
    }

    //This method returns a team object with data scraped from the teamUrl supplied
    public static TeamJsonScraper getTeam(String teamUrl) {
        try {
            //Create new web client
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            HtmlPage page = client.getPage(teamUrl);

            //Reads the team data from the WebClient
            String name = ((HtmlElement) page.getFirstByXPath("//*[@id=\"verein_head\"]/div/div[1]/div[1]/div/div[1]/h1/span")).asText();
            String stadiumName = ((HtmlElement) page.getFirstByXPath("//*[@id=\"verein_head\"]/div/div[1]/div[2]/div/div[2]/p[2]/span[2]/a")).asText();
            String stadiumCapacityString = ((HtmlElement) page.getFirstByXPath("//*[@id=\"verein_head\"]/div/div[1]/div[2]/div/div[2]/p[2]/span[2]/span")).asText().replace(".", "");
            String league = ((HtmlImage) page.getFirstByXPath("//*[@id=\"verein_head\"]/div/div[1]/div[4]/div[1]/a/img")).getAltAttribute();

            //Formats the data for the stadium capacity to allow it to be stored as an integer
            int stadiumCapacity = Integer.parseInt(stadiumCapacityString.split(" ")[0]);

            //Returns the team object
            return new TeamJsonScraper(name, stadiumName, stadiumCapacity, league);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TeamJsonScraper();
    }

    //This method returns a team object with data scraped from the teamUrl supplied
    public static PlayerJsonScraper getPlayer(String playerUrl) {
        PlayerJsonScraper player = new PlayerJsonScraper();

        try {
            //Creates a new WebClient
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            HtmlPage page = client.getPage(playerUrl);

            //Declare and initialize the variables so that they are filled even if there is a read problem
            String dateOfBirth = null;
            int age = 0;
            String position = null;
            String nationality = null;
            String team = null;

            //Iterate over the table headers
            for (int i = 1; i < 20; i++) {
                HtmlElement tableData = null;
                HtmlElement tableHeader = null;
                if ((page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/td")) == null) {
                    tableData = (page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[1]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/td"));
                    tableHeader = (page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[1]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/th"));
                } else {
                    tableData = (page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/td"));
                    tableHeader = (page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[" + i + "]/th"));
                }
                switch (tableHeader.asText()) {
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
                        team = ((HtmlElement) tableData.getFirstByXPath("//td/a[2]")).asText();
                        ;
                        i = 21;
                        break;
                }
            }

            String name;
            if ((page.getFirstByXPath("//*[@id=\"main\"]/div[8]/div/div/div[1]/div/div/h1")) == null) {
                name = ((HtmlElement) page.getFirstByXPath("//*[@id=\"main\"]/div[8]/div/div/div[2]/div/div/h1")).asText();
            } else {
                name = ((HtmlElement) page.getFirstByXPath("//*[@id=\"main\"]/div[8]/div/div/div[1]/div/div/h1")).asText();
            }

            int value = 0;
            if (page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[1]/div/div[4]/div[1]/div[1]/div/div[1]/div[2]/a[1]") != null) {
                value = formatValue(((HtmlElement) page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[2]/div[2]/div[1]/div/div[4]/div[1]/div[1]/div/div[1]/div[2]/a[1]")).asText().replace(".", ""));
            } else if (page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[1]/div[2]/div[1]/div/div[4]/div[1]/div[1]/div/div[1]/div[2]/a[1]") != null) {
                value = formatValue(((HtmlElement) page.getFirstByXPath("//*[@id=\"main\"]/div[11]/div[1]/div[1]/div[2]/div[1]/div/div[4]/div[1]/div[1]/div/div[1]/div[2]/a[1]")).asText().replace(".", ""));
            }

            String yw = null;
            assert position != null;
            if (page.getFirstByXPath("//*[@id=\"yw2\"]/table/tfoot/tr/td[5]") != null) {
                yw = "yw2";
            } else if (page.getFirstByXPath("//*[@id=\"yw1\"]/table/tfoot/tr/td[5]") != null) {
                yw = "yw1";
            }

            int gamesPlayed = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[3]")).asText());

            //Initialise the player stats
            int cleanSheets = 0;
            int goalsConceded = 0;
            int goalsScored = 0;
            int assists = 0;

            if (position.equals("Goalkeeper") && yw != null) {
                cleanSheets = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[5]")).asText());
                goalsConceded = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[4]")).asText());
            } else if (yw != null) {
                goalsScored = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[4]")).asText());
                assists = stringToInt(((HtmlElement) page.getFirstByXPath("//*[@id=\"" + yw + "\"]/table/tfoot/tr/td[5]")).asText());
            }
            player = new PlayerJsonScraper(name, dateOfBirth, age, position, nationality, team, gamesPlayed, cleanSheets, goalsConceded, goalsScored, assists, value, playerUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Written player to JSON - " + player.name);
        return player;
    }

    //Method to format data from string to int and remote dash
    public static int stringToInt(String str){
        if(str.equals("-")){
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }

    //Formats the value variable to turn it into a long
    public static int formatValue(String str){
        System.out.println("str: " + str);
        str = str.substring(1);
        if(str.contains("m")){
            str = str.replace("m","");
            return Integer.parseInt(str) * 10000;
        }
        if(str.contains("k")){
            str = str.replace("k","");
            return Integer.parseInt(str) * 1000;
        }
        if(str.contains("Th")){
            System.out.println("is th");
            str = str.replace("Th","");
            return Integer.parseInt(str) * 1000;
        }
        return Integer.parseInt(str);
    }

    //This method returns a task object which will write files to the JSON depending on the dbsize selected by the user
    public static Task returnTaskJsonCreation(String dbSize, String jsonFileName){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                ArrayList<String> teamLinks = new ArrayList<>();
                ArrayList<PlayerJsonScraper> playerList = new ArrayList<>();
                ArrayList<TeamJsonScraper> teamList = new ArrayList<>();
                System.out.println("Start Json Creation");

                //These are all of the urls that are used to scrape team and player data from
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

                //Iterate over the team URLs
                for (String teamLink : teamLinks) {
                    //Check if the task has been cancelled and exit the method if it has
                    if(isCancelled()){
                        System.out.println("Is cancelled");
                        break;
                    }
                    //Read team data from the teamlink page and add to the team list array which will be written later in the method
                    teamList.add(getTeam(baseUrl + teamLink));

                    for (String playerLink : getPlayerLinks(baseUrl + teamLink)) {
                        //These players have insufficient data to use so will be skipped
                        if( playerLink.equals("/ahmadou-dia/profil/spieler/611673") ||
                                playerLink.equals("/callum-pearson/profil/spieler/547036") ||
                                playerLink.equals("/eiji-kawashima/profil/spieler/77383")  ||
                                playerLink.equals("/anthony-rouault/profil/spieler/690799")  ||
                                playerLink.equals("/boris-essele/profil/spieler/694499")  ||
                                playerLink.equals("/darell-tokpa/profil/spieler/655414")  ||
                                playerLink.equals("/louis-pelletier/profil/spieler/616283")) {

                        }
                        else {
                            //Read player date from the playerLink page and write it to the playerList arrayList to be written later in the method
                            playerList.add(getPlayer(baseUrl + playerLink));
                        }
                    }
                    //Updates the progress value of the task which is linked to the progress bar
                    updateProgress++;
                    updateProgress(updateProgress, teamLinks.size());
                }
                try {
                    //Writes the players to the json file
                    writeObjects(teamList, playerList, jsonFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
}
