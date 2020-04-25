package FootballLeague.FootballLeagueBackend.WebScraper;

import FootballLeague.FootballLeagueBackend.Tactic;
import FootballLeague.FootballLeagueBackend.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Player;
import static FootballLeague.FootballLeagueBackend.Player.writePlayer;
import static FootballLeague.FootballLeagueBackend.Tactic.writeTactic;
import static FootballLeague.FootballLeagueBackend.Team.readAllTeams;
import static FootballLeague.FootballLeagueBackend.Team.writeTeam;

public class DatabasePopulator {

    public static void insertTeams(){
        writeTeam(new Team("Manchester City", "Premier League", "XXXXXX"));
        writeTeam(new Team("Liverpool FC", "Premier League", "XXXXXX"));
        writeTeam(new Team("Tottenham Hotspur", "Premier League", "XXXXXX"));
        writeTeam(new Team("Chelsea FC", "Premier League", "XXXXXX"));
        writeTeam(new Team("Manchester United", "Premier League", "XXXXXX"));
        writeTeam(new Team("Arsenal FC", "Premier League", "XXXXXX"));
        writeTeam(new Team("Everton FC", "Premier League", "XXXXXX"));
        writeTeam(new Team("Leicester City", "Premier League", "XXXXXX"));
        writeTeam(new Team("Wolverhampton Wanderers", "Premier League", "XXXXXX"));
        writeTeam(new Team("West Ham United", "Premier League", "XXXXXX"));
        writeTeam(new Team("AFC Bournemouth", "Premier League", "XXXXXX"));
        writeTeam(new Team("Newcastle United", "Premier League", "XXXXXX"));
        writeTeam(new Team("Aston Villa", "Premier League", "XXXXXX"));
        writeTeam(new Team("Watford FC", "Premier League", "XXXXXX"));
        writeTeam(new Team("Brighton & Hove Albion", "Premier League", "XXXXXX"));
        writeTeam(new Team("Southampton FC", "Premier League", "XXXXXX"));
        writeTeam(new Team("Crystal Palace", "Premier League", "XXXXXX"));
        writeTeam(new Team("Burnley FC", "Premier League", "XXXXXX"));
        writeTeam(new Team("Norwich City", "Premier League", "XXXXXX"));
        writeTeam(new Team("Sheffield United", "Premier League", "XXXXXX"));

        writeTactic(new Tactic("01", "00", 0.8, 0.4, "442", "Attacking"));
        writeTactic(new Tactic("02", "00", 0.6, 0.5, "433", "Park the bus"));
        writeTactic(new Tactic("03", "00", 0.1, 0.1, "532", "Gengenpress"));
    }

    public static void jsonToDB(String jsonName, String dbName){

        //Set current save to the db name provided as a parameter
        try {
            GameState.writeSaveName("../BaseGames/" + dbName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        insertTeams();

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();


        try (FileReader reader = new FileReader("src/main/resources/BaseGames/" + jsonName))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray playerList = (JSONArray) obj;

            //Iterate over player list
            playerList.forEach( emp -> parsePlayerObject( (JSONObject) emp ) );

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parsePlayerObject(JSONObject player){
        //Get employee object within list
        JSONObject playerObject = (JSONObject) player.get("player");

        //Get employee first name
        String name = (String) playerObject.get("name");
        System.out.println(name);

        if(name == null){
            System.out.println("Null");
            return;
        }

        if(name.equals("")){
            System.out.println("Blank");
            return;
        }

        //Get employees first name
        String forename = name.split(" ")[0];

        //Get employees surname
        String surname = null;
        if(name.split(" ").length < 2){
            surname = name.split(" ")[0];
        } else if(name.split(" ").length > 2){
            surname = name.split(" ")[1] + " " + name.split(" ")[2];
        } else {
            surname = name.split(" ")[1];
        }

        //Get team
        String team = (String) playerObject.get("team");

        if(team.contains("U23")){
            team = team.substring(0, team.length() - 4);
        }

        //Get teamCode
        String teamCode = readAllTeams("WHERE teamName='" + team + "'").get(0).getTeamCode();

        Player player1 = new Player(forename, surname, true, teamCode);

        System.out.println("Forename: " + player1.getForename());
        System.out.println("Surname: " + player1.getSurname());
        System.out.println("Team Code: " + player1.getTeamCode());
        writePlayer(player1);
    }
}
