package FootballLeague.FootballLeagueBackend.WebScraper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JsonScraperOperations {

    //This method is only used to write the json to store the current save name
    public static void writePlayer(ArrayList<PlayerJsonScraper> players, String dbSize) throws IOException {

        //Add employees to list
        JSONArray playerList = new JSONArray();

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

        //Used to add the date time to the file name to make them unique
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
        String formattedDate = myDateObj.format(myFormatObj);

        Path filePath = Paths.get("src/main/resources/BaseGames/playerJson" + dbSize.trim() + formattedDate + ".json");

        //Check if the file exists
        if(!Files.exists(filePath)) {
            //If file doesn't exist the create new file
            Files.createFile(filePath);
        }

        //Write to the file
        Files.write(filePath, playerList.toJSONString().getBytes());
    }
}