package FootballLeague.FootballLeagueBackend.WebScraper;

import FootballLeague.FootballLeagueBackend.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

import static FootballLeague.FootballLeagueBackend.Club.writeClub;
import static FootballLeague.FootballLeagueBackend.Player.writePlayer;
import static FootballLeague.FootballLeagueBackend.Tactic.writeTactic;
import static FootballLeague.FootballLeagueBackend.Team.readAllTeams;
import static FootballLeague.FootballLeagueBackend.Team.writeTeam;
import static FootballLeague.FootballLeagueBackend.Venue.writeVenue;

public class DatabasePopulator {

    public static void addDefaultTactics(){
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

        //This method is used to write the default tactics to the database
        //These tactics are assigned to the AI controlled teams
        addDefaultTactics();

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

    //Takes the data from the JSON file and writes it to the database
    //Used to write team, venue and club data
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

        //If there was an error when reading the players name then don't add that player to the database
        if(playerObject.get("name") == null){
            return;
        }

        //Get player first name
        String name = sanitiseData((String) playerObject.get("name"));
        System.out.println(name);

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

        //Read the database to find the team code of the team that the player plays for
        //If the team cannot be find due to corrupt data then it will be set to "BROKEN - team" to help debugging
        String teamCode = "BROKEN - " + team;
        try{
            teamCode = readAllTeams("WHERE teamName='" + team + "'").get(0).getTeamCode();
        } catch (Exception e){
            e.printStackTrace();
        }

        //Write the created player to the database
        writePlayer(new Player(forename, surname, true, teamCode));
    }

    //This method is used to remove ' from the data as it was causing errors with the SQL
    public static String sanitiseData(String str){
        return str.replace("'","");
    }
}
