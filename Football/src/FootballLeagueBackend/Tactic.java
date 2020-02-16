package FootballLeagueBackend;
import java.util.ArrayList;
import java.util.Arrays;

public class Tactic {
    //TODO tactic should probably have a name variable to users can name the tactics they create
    String tacticCode;
    String startingXICode;
    //Scale from 0 to 1 of how good the team is at scoring goals
    double attackScore;
    //Scale from 0 to 1 of how good the team is at defending
    double defenceScore;
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

    //Constructors
    Tactic(){}

    public Tactic(String startingXICode, double attackScore, double defenceScore, String formation, String playStyle){
        this.tacticCode = (String.format("%03d", codeIteration) + "TAC").toUpperCase();
        this.startingXICode = startingXICode;
        this.attackScore = attackScore;
        this.defenceScore = defenceScore;
        this.formation = formation;
        this.playStyle = playStyle;
        codeIteration++;
    }

    public Tactic(String tacticCode, String startingXICode, double attackScore, double defenceScore, String formation, String playStyle){
        this.tacticCode = tacticCode.toUpperCase();
        this.startingXICode = startingXICode;
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
    //TODO add function to calculate attack/defence scores based on the other inputs (players, formation and playstyle)
}
