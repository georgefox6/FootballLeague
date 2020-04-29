package FootballLeague.FootballLeagueBackend.WebScraper;

import FootballLeague.FootballLeagueBackend.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.crypto.spec.PSource;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static FootballLeague.FootballLeagueBackend.Club.writeClub;
import static FootballLeague.FootballLeagueBackend.Player.writePlayer;
import static FootballLeague.FootballLeagueBackend.Tactic.writeTactic;
import static FootballLeague.FootballLeagueBackend.Team.readAllTeams;
import static FootballLeague.FootballLeagueBackend.Team.writeTeam;
import static FootballLeague.FootballLeagueBackend.Venue.writeVenue;

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
        System.out.println("JSON NAME : " + jsonName);

        //Set current save to the db name provided as a parameter
        try {
            GameState.writeSaveName("../BaseGames/" + dbName);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        insertTeams();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();


        try (FileReader reader = new FileReader("src/main/resources/BaseGames/" + jsonName))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray playerList = (JSONArray) obj;

            for(Object o : playerList){
                JSONObject jsonObject = (JSONObject) o;
                //if it is a team object then call parse team method
                if(jsonObject.get("team") != null ) {
                    parseTeamObject(jsonObject);
                }
                //if it is a player object then call parse player method
                else if(jsonObject.get("player") != null ){
                    parsePlayerObject(jsonObject);
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void parseTeamObject(JSONObject team){
        JSONObject teamObject = (JSONObject) team.get("team");

        String venueName = sanitiseData((String) teamObject.get("venueName"));
        int capacity = Integer.parseInt((String)teamObject.get("venueCapacity"));

        Venue venue = new Venue(venueName, capacity, 20);
        System.out.println("Venue code: " + venue.getVenueCode());

        String clubName = (String) teamObject.get("name");

        Club club = new Club(clubName, venue.getVenueCode());

        String league = (String) teamObject.get("league");

        Team team1 = new Team(clubName, league, club.getClubCode());

        writeVenue(venue);

        writeClub(club);

        writeTeam(team1);

        System.out.println("team written: " + clubName);

    }

    private static void parsePlayerObject(JSONObject player){
        //Get player object within list
        JSONObject playerObject = (JSONObject) player.get("player");

        //Get player first name
        String name = sanitiseData((String) playerObject.get("name"));
        System.out.println(name);

        //If there was an error when reading the players name then don't add that player to the database
        if(name == null){
            return;
        }

        //Get players first name
        String forename = name.split(" ")[0];

        //Get employees surname - conditionals used to allow double barrelled names
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

        //Remove "U23" from the team name
        if(team.contains("U23")){
            team = team.substring(0, team.length() - 4);
        }




        System.out.println("Team needed: " + team);

        if(team.equals("Al-Waab")){
            team = "Tottenham Hotspur";
        }
        if(team.equals("Happy Birthday")){
            team = "AFC Bournemouth";
        }

        String teamCode = "";

        if(readAllTeams("WHERE teamName='" + team + "'") == null){
            teamCode = "BROKEN - " + team;
        } else {
            //Get teamCode
            teamCode = readAllTeams("WHERE teamName='" + team + "'").get(0).getTeamCode();
        }


        //Write the created player to the database
        writePlayer(new Player(forename, surname, true, teamCode));


    }

    public static String sanitiseData(String str){
        return str.replace("'","");
    }
}
