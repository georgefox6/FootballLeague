package FootballLeagueBackend;

import java.util.ArrayList;
import java.util.Arrays;

public class Tactic {
    String tacticCode;
    ArrayList<Player> startingXI;
    ArrayList<Player> substitutionBench;
    //Scale from 0 to 1 of how good the team is at scoring goals
    int attackScore;
    //Scale from 0 to 1 of how good the team is at defending
    int defenceScore;
    String formation;
    String playStyle;

    static int codeIteration;

    static {
        codeIteration = Database.countTactics();
    }

    //Getters
    public String getTacticCode() {
        return tacticCode;
    }
    public ArrayList<Player> getStartingXI() {
        return startingXI;
    }
    public ArrayList<Player> getSubstitutionBench() {
        return substitutionBench;
    }
    public int getAttackScore() {
        return attackScore;
    }
    public int getDefenceScore() {
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
    public void setStartingXI(ArrayList<Player> startingXI) {
        this.startingXI = startingXI;
    }
    public void setSubstitutionBench(ArrayList<Player> substitutionBench) {
        this.substitutionBench = substitutionBench;
    }
    public void setAttackScore(int attackScore) {
        this.attackScore = attackScore;
    }
    public void setDefenceScore(int defenceScore) {
        this.defenceScore = defenceScore;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public void setPlayStyle(String playStyle) {
        this.playStyle = playStyle;
    }

    //Constructors
    Tactic(){}

    Tactic(ArrayList<Player> startingXI, ArrayList<Player> substitutionBench, int attackScore, int defenceScore, String formation, String playStyle){
        this.tacticCode = (String.format("%03d", codeIteration) + "TAC").toUpperCase();
        this.startingXI = startingXI;
        this.substitutionBench = substitutionBench;
        this.attackScore = attackScore;
        this.defenceScore = defenceScore;
        this.formation = formation;
        this.playStyle = playStyle;
    }

    Tactic(String tacticCode, ArrayList<Player> startingXI, ArrayList<Player> substitutionBench, int attackScore, int defenceScore, String formation, String playStyle){
        this.tacticCode = tacticCode.toUpperCase();
        this.startingXI = startingXI;
        this.substitutionBench = substitutionBench;
        this.attackScore = attackScore;
        this.defenceScore = defenceScore;
        this.formation = formation;
        this.playStyle = playStyle;
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
            StartingXI.add(Database.readPlayer(playerCodesList.get(i)));
        }
        return StartingXI;
    }
}
