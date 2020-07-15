package FootballLeague.FootballLeagueBackend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import FootballLeague.LogHandler.LogHandler;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;

public class Tactic {

    public static LogHandler log = new LogHandler("FootballLeague.FootballLeagueBackend.Tactic");
    
    //TODO tactic should probably have a name variable to users can name the tactics they create
    String tacticCode;
    String startingXICode;
    //Scale from 0 to 1 of how good the team is at scoring goals
    double attackScore;
    //Scale from 0 to 1 of how good the team is at defending
    double defenceScore;
    //Scale from 0 to 1 of how creative the team is
    double creativeScore;
    String formation;
    String playStyle;
    public String name;

    static int codeIteration;

    static {
        codeIteration = countTactic();
    }

    //Getters
    public String getTacticCode() {
        return tacticCode;
    }
    public String getStartingXICode() {
        return startingXICode;
    }
    public double getAttackScore() {
        return attackScore;
    }
    public double getDefenceScore() {
        return defenceScore;
    }
    public String getFormation() {
        return formation;
    }
    public String getPlayStyle() {
        return playStyle;
    }


    //Setters


    public void setTacticCode(String tacticCode) {
        this.tacticCode = tacticCode;
    }

    public void setStartingXICode(String startingXICode) {
        this.startingXICode = startingXICode;
    }

    public void setAttackScore(double attackScore) {
        this.attackScore = attackScore;
    }

    public void setDefenceScore(double defenceScore) {
        this.defenceScore = defenceScore;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public void setPlayStyle(String playStyle) {
        this.playStyle = playStyle;
    }

    public static void setCodeIteration(int codeIteration) {
        Tactic.codeIteration = codeIteration;
    }

    //Constructors
    public Tactic(){}

    public Tactic(String startingXICode, double attackScore, double defenceScore, double creativeScore, String formation, String playStyle, String name){
        this.tacticCode = (String.format("%03d", codeIteration) + "TAC").toUpperCase();
        this.startingXICode = startingXICode;
        this.attackScore = attackScore;
        this.defenceScore = defenceScore;
        this.creativeScore = creativeScore;
        this.formation = formation;
        this.playStyle = playStyle;
        this.name = name;
        codeIteration++;
    }

    public Tactic(String tacticCode, String startingXICode, double attackScore, double defenceScore, double creativeScore, String formation, String playStyle, String name){
        this.tacticCode = tacticCode.toUpperCase();
        this.startingXICode = startingXICode;
        this.attackScore = attackScore;
        this.defenceScore = defenceScore;
        this.creativeScore = creativeScore;
        this.formation = formation;
        this.playStyle = playStyle;
        this.name = name;
    }

    //This method is used to create a string of the player codes of the startingXI delimited by commas
    //This is how the startingXI will be stored in the DB e.g 002GFO,007ERO,032WRO
    public static String startingXIToCode(ArrayList<Player> startingXI){
        String startingXICode = startingXI.get(0).getPlayerCode();
        for (int i = 1; i < startingXI.size(); i++){
            startingXICode = startingXICode + "," + startingXI.get(i).getPlayerCode();
        }
        return startingXICode;
    }

    //This method is used to return an array list of players when a string of player codes as it will be stored in the DB in this form
    public static ArrayList<Player> codeToStartingXI(String playerCodes){
        ArrayList<Player> StartingXI = new ArrayList<>();
        ArrayList<String> playerCodesList = (ArrayList<String>) Arrays.asList(playerCodes.split(","));
        for(int i = 0; i < playerCodesList.size(); i++){
            StartingXI.add(Player.readPlayer(playerCodesList.get(i)));
        }
        return StartingXI;
    }
    //TODO add function to calculate attack/defence scores based on the other inputs (players, formation and playstyle)

    public static Tactic readTactic(String tacticCode){
        ResultSet result = DatabaseConnection.readQuery("tactic", "tacticCode='" + tacticCode);
        try {
            assert result != null;
            if(result.next()){
                return new Tactic(tacticCode, result.getString("startingXICode"), result.getDouble("attackScore"), result.getDouble("defenceScore"), result.getDouble("creativeScore"), result.getString("formation"), result.getString("playStyle"), result.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return null;
    }

    //For example use "WHERE formation='4-4-2'" to get tactics with the formation 4-4-2 or " " to get all tactics
    public static ArrayList<Tactic> readAllTactics(String clause){
        ArrayList<Tactic> tactics = new ArrayList<>();
        try{
            ResultSet rs = readAllQuery("tactic", clause);
            assert rs != null;
            while(rs.next()){
                tactics.add(new Tactic(rs.getString("tacticCode"), rs.getString("startingXICode"), rs.getDouble("attackScore"), rs.getDouble("defenceScore"), rs.getDouble("creativeScore"), rs.getString("formation"), rs.getString("playStyle"), rs.getString("name")));
            }
            log.log("Tactic Size : " + tactics.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return tactics;
    }

    public static boolean writeTactic(Tactic tactic){
        String values = String.format("'%s', '%s', '%s', '%s', '%s', '%s'", tactic.getTacticCode(), tactic.getStartingXICode(), tactic.getAttackScore(), tactic.getDefenceScore(), tactic.getFormation(), tactic.getPlayStyle());
        return DatabaseConnection.writeQuery("tactic", values);
    }

    public static void updateTactic(Tactic tactic){
        String values = String.format("startingXICode='%s', attackScore='%s', defenceScore='%s', formation='%s', playStyle='%s' WHERE tacticCode='%s'", tactic.getStartingXICode(), tactic.getAttackScore(), tactic.getDefenceScore(), tactic.getFormation(), tactic.getPlayStyle(), tactic.getTacticCode());
        updateQuery("tactic", values);
    }

    public static int countTactic(){
        return countQuery("tactic", "tacticCode");
    }
}
