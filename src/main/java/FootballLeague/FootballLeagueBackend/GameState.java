package FootballLeague.FootballLeagueBackend;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameState {

    private static final String filePath = "src/main/resources/SaveGames/";

    //This method is only used to write the json to store the current save name
    public static void writeSaveName(String saveName) throws IOException {
        JSONObject object = new JSONObject();

        String fileName = "src/main/resources/SaveGames/currentSave.json";

        object.put("saveName", saveName);

        Files.write(Paths.get(fileName), object.toJSONString().getBytes());
    }

    public static String readSaveName() throws IOException, ParseException {
        String filename = "src/main/resources/SaveGames/currentSave.json";
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        JSONObject object = (JSONObject)jsonParser.parse(reader);
        return object.get("saveName").toString();
    }

    public static void initGameState(String team, String saveName) throws IOException {
        JSONObject object = new JSONObject();

        String fileName = filePath + saveName + ".json";

        object.put("team", team);
        object.put("saveName", saveName);
        object.put("databaseName", saveName + ".db");
        object.put("gameWeek", "0");
        object.put("gameYear", "0");

        Files.write(Paths.get(fileName), object.toJSONString().getBytes());
    }

    public static void updateTeam(String saveName, String newTeam) throws IOException, ParseException {
        JSONObject object = new JSONObject();

        String fileName = filePath + saveName + ".json";

        object.put("team", newTeam);
        object.put("saveName", saveName);
        object.put("databaseName", readDatabaseName(saveName));
        object.put("gameWeek", readGameWeek(saveName));
        object.put("gameYear", readGameYear(saveName));
        Files.write(Paths.get(fileName), object.toJSONString().getBytes());
    }

    public static void updateDatabaseName(String saveName, String newDatabaseName) throws IOException, ParseException {
        JSONObject object = new JSONObject();

        String fileName = filePath + saveName + ".json";

        object.put("team", readTeam(saveName));
        object.put("saveName", saveName);
        object.put("databaseName", newDatabaseName);
        object.put("gameWeek", readGameWeek(saveName));
        object.put("gameYear", readGameYear(saveName));
        Files.write(Paths.get(fileName), object.toJSONString().getBytes());
    }

    public static void updateGameWeek(String saveName, String gameWeek) throws IOException, ParseException {
        JSONObject object = new JSONObject();

        String fileName = filePath + saveName + ".json";

        object.put("team", readTeam(saveName));
        object.put("saveName", saveName);
        object.put("databaseName", readDatabaseName(saveName));
        object.put("gameWeek", gameWeek);
        object.put("gameYear", readGameYear(saveName));
        Files.write(Paths.get(fileName), object.toJSONString().getBytes());
    }

    public static void nextGameWeek(String saveName) throws IOException, ParseException {
        JSONObject object = new JSONObject();

        String fileName = filePath + saveName + ".json";

        String nextGameWeek = String.valueOf(Integer.parseInt(readGameWeek(saveName)) + 1);

        object.put("team", readTeam(saveName));
        object.put("saveName", saveName);
        object.put("databaseName", readDatabaseName(saveName));
        object.put("gameWeek", nextGameWeek);
        object.put("gameYear", readGameYear(saveName));

        Files.write(Paths.get(fileName), object.toJSONString().getBytes());
    }

    public static void updateGameYear(String saveName, String gameYear) throws IOException, ParseException {
        JSONObject object = new JSONObject();

        String fileName = filePath + saveName + ".json";

        object.put("team", readTeam(saveName));
        object.put("saveName", saveName);
        object.put("databaseName", readDatabaseName(saveName));
        object.put("gameWeek", readGameWeek(saveName));
        object.put("gameYear", gameYear);
        Files.write(Paths.get(fileName), object.toJSONString().getBytes());
    }

    public static void nextGameYear(String saveName) throws IOException, ParseException {
        JSONObject object = new JSONObject();

        String fileName = filePath + saveName + ".json";

        String nextGameYear = String.valueOf(Integer.parseInt(readGameYear(saveName)) + 1);

        object.put("team", readTeam(saveName));
        object.put("saveName", saveName);
        object.put("databaseName", readDatabaseName(saveName));
        object.put("gameWeek", readGameWeek(saveName));
        object.put("gameYear", nextGameYear);
        Files.write(Paths.get(fileName), object.toJSONString().getBytes());
    }

    private static Object readJson(String filename) throws IOException, ParseException {
        filename = filePath + filename + ".json";
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }


    public static String readDatabaseName(String filename) throws IOException, ParseException {
        JSONObject object = (JSONObject) readJson(filename);
        return object.get("databaseName").toString();
    }

    public static String readGameWeek(String filename) throws IOException, ParseException {
        JSONObject object = (JSONObject) readJson(filename);
        return object.get("gameWeek").toString();
    }

    public static String readGameYear(String filename) throws IOException, ParseException {
        JSONObject object = (JSONObject) readJson(filename);
        return object.get("gameYear").toString();
    }

    public static String readTeam(String filename) throws IOException, ParseException {
        JSONObject object = (JSONObject) readJson(filename);
        return object.get("team").toString();
    }


    public static void main(String[] args) throws IOException, ParseException {
    }
}
