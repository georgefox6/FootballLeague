package FootballLeague.FootballLeagueBackend.WebScraper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonScraperOperations {

    //This method is only used to write the json to store the current save name
    public static void writePlayer(ArrayList<TeamJsonScraper> teams, ArrayList<PlayerJsonScraper> players, String jsonFileName) throws IOException {

        //Add players to list
        JSONArray playerList = new JSONArray();

        for(TeamJsonScraper team : teams){
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Writing team to JSON");
            System.out.println(team.teamName);
            System.out.println(team.venueName);
            System.out.println(team.venueCapacity);
            JSONObject teamDetails = new JSONObject();
            teamDetails.put("name", team.teamName);
            teamDetails.put("venueName", team.venueName);
            teamDetails.put("venueCapacity", String.valueOf(team.venueCapacity));
            teamDetails.put("league", team.league);

            JSONObject teamObject = new JSONObject();
            teamObject.put("team", teamDetails);

            playerList.add(teamObject);
        }

        for(PlayerJsonScraper player : players){
            JSONObject playerDetails = new JSONObject();
            playerDetails.put("name", player.name);
            playerDetails.put("dateOfBirth", player.dateOfBirth);
            playerDetails.put("age", String.valueOf(player.age));
            playerDetails.put("position", player.position);
            playerDetails.put("nationality", player.nationality);
            playerDetails.put("team", player.team);
            playerDetails.put("gamesPlayed", String.valueOf(player.gamesPlayed));
            playerDetails.put("goalsScored", String.valueOf(player.goalsScored));
            playerDetails.put("cleanSheets", String.valueOf(player.cleanSheets));
            playerDetails.put("goalsConceded", String.valueOf(player.goalsConceded));
            playerDetails.put("value", String.valueOf(player.value));
            playerDetails.put("playerUrl", player.playerUrl);

            JSONObject playerObject = new JSONObject();
            playerObject.put("player", playerDetails);

            playerList.add(playerObject);
        }



        Path filePath = Paths.get("src/main/resources/BaseGames/" + jsonFileName + ".json");

        //Check if the file exists
        if(!Files.exists(filePath)) {
            //If file doesn't exist the create new file
            Files.createFile(filePath);
        }

        //Write to the file
        Files.write(filePath, playerList.toJSONString().getBytes());
    }
}