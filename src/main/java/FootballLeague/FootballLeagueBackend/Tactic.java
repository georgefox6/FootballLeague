package FootballLeague.FootballLeagueBackend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;
import static FootballLeague.FootballLeagueBackend.Player.readAllPlayers;
import static FootballLeague.FootballLeagueBackend.StartingXI.writeStartingXI;

public class Tactic {

    public static Logger logger = LogManager.getLogger("com.josh");

    String tacticCode;
    String startingXICode;
    //Scale from 0 to 1 of how good the team is at scoring goals
    double attackScore;
    double creativeScore;
    //Scale from 0 to 1 of how good the team is at defending
    double defenceScore;
    String formation;
    String playStyle;
    String name;

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
    public double getCreativeScore(){ return creativeScore;}
    public double getDefenceScore() {
        return defenceScore;
    }
    public String getFormation() {
        return formation;
    }
    public String getPlayStyle() {
        return playStyle;
    }
    public String getName(){return name;}


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

    public void setCreativeScore(double creativeScore) { this.creativeScore = creativeScore;}

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

    public void setName(String name){this.name = name;}

    //Constructors
    public Tactic(){}

    public Tactic(String startingXICode, double attackScore, double creativeScore, double defenceScore, String formation, String playStyle, String name){
        this.tacticCode = (String.format("%03d", codeIteration) + "TAC").toUpperCase();
        this.startingXICode = startingXICode;
        this.attackScore = attackScore;
        this.creativeScore = creativeScore;
        this.defenceScore = defenceScore;
        this.formation = formation;
        this.playStyle = playStyle;
        this.name = name;
        codeIteration++;
    }

    public Tactic(String tacticCode, String startingXICode, double attackScore, double creativeScore, double defenceScore, String formation, String playStyle, String name){
        this.tacticCode = tacticCode.toUpperCase();
        this.startingXICode = startingXICode;
        this.attackScore = attackScore;
        this.creativeScore = creativeScore;
        this.defenceScore = defenceScore;
        this.formation = formation;
        this.playStyle = playStyle;
        this.name = name;
    }

    public String toString(){
        return this.name;
    }

    public static Tactic generateBestTactic(String teamCode){
        ArrayList<Player> teamPlayers = readAllPlayers("WHERE teamCode='" + teamCode + "'");
        Collections.sort(teamPlayers);
        ArrayList<Player> goalkeeper = new ArrayList<>();
        ArrayList<Player> defenders = new ArrayList<>();
        ArrayList<Player> midfielders = new ArrayList<>();
        ArrayList<Player> forwards = new ArrayList<>();
        double defensiveScore = 0.0;
        double creativeScore = 0.0;
        double attackingScore = 0.0;
        for(Player player : teamPlayers){
            switch(player.getPosition()){
                case"Goalkeeper":
                    if(goalkeeper.size() == 0){
                        goalkeeper.add(player);
                    }
                    defensiveScore += player.getDefensiveStat();
                    creativeScore += player.getCreativityStat();
                    attackingScore += player.getAttackingStat();
                    break;
                case"Defender":
                    if(defenders.size() + midfielders.size() + forwards.size() >= 10){
                        break;
                    }
                    if(defenders.size() < 3){
                        defenders.add(player);
                    } else if(defenders.size() + midfielders.size() + forwards.size() < 10 && defenders.size() < 4){
                        defenders.add(player);
                    } else if(defenders.size() + midfielders.size() + forwards.size() < 10 && defenders.size() < 5 && forwards.size() < 3 && defenders.size() + midfielders.size() < 8){
                        defenders.add(player);
                    }
                    defensiveScore += player.getDefensiveStat();
                    creativeScore += player.getCreativityStat();
                    attackingScore += player.getAttackingStat();
                    break;
                case"Midfielder":
                    if(defenders.size() + midfielders.size() + forwards.size() >= 10){
                        break;
                    }
                    if(midfielders.size() < 3){
                        midfielders.add(player);
                    } else if(defenders.size() + midfielders.size() + forwards.size() < 10 && defenders.size() <= 4 && midfielders.size() < 4) {
                        midfielders.add(player);
                    }
                    defensiveScore += player.getDefensiveStat();
                    creativeScore += player.getCreativityStat();
                    attackingScore += player.getAttackingStat();
                    break;
                case"Forward":
                    if(defenders.size() + midfielders.size() + forwards.size() >= 10){
                        break;
                    }
                    if(forwards.size() < 2){
                        forwards.add(player);
                    } else if(defenders.size() + midfielders.size() + forwards.size() < 10 && forwards.size() < 3 && defenders.size() < 5){
                        forwards.add(player);
                    }
                    defensiveScore += player.getDefensiveStat();
                    creativeScore += player.getCreativityStat();
                    attackingScore += player.getAttackingStat();
                    break;
            }
        }
        String formation = defenders.size() + "-" + midfielders.size() + "-" + forwards.size();
        ArrayList<Player> startingPlayers = new ArrayList<>();
        startingPlayers.addAll(goalkeeper);
        startingPlayers.addAll(defenders);
        startingPlayers.addAll(midfielders);
        startingPlayers.addAll(forwards);
        StartingXI startingXI = new StartingXI(startingPlayers.get(0),startingPlayers.get(1),startingPlayers.get(2),startingPlayers.get(3),startingPlayers.get(4),startingPlayers.get(5),startingPlayers.get(6),startingPlayers.get(7),startingPlayers.get(8),startingPlayers.get(9),startingPlayers.get(10));
        writeStartingXI(startingXI);
        return new Tactic(startingXI.getStartingXICode(), attackingScore, creativeScore, defensiveScore, formation, "Tiki Taka", "");
    }

    public static Tactic readTactic(String tacticCode){
        ResultSet result = DatabaseConnection.readQuery("tactic", "tacticCode='" + tacticCode);
        try {
            assert result != null;
            if(result.next()){
                return new Tactic(tacticCode, result.getString("startingXICode"), result.getDouble("attackScore"), result.getDouble("creativeScore"), result.getDouble("defenceScore"), result.getString("formation"), result.getString("playStyle"), result.getString("name"));
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
                tactics.add(new Tactic(rs.getString("tacticCode"), rs.getString("startingXICode"), rs.getDouble("attackScore"), rs.getDouble("creativeScore"), rs.getDouble("defenceScore"), rs.getString("formation"), rs.getString("playStyle"), rs.getString("name")));
            }
            logger.info("Tactic Size : " + tactics.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return tactics;
    }

    public static boolean writeTactic(Tactic tactic){
        String values = String.format("'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'", tactic.getTacticCode(), tactic.getStartingXICode(), tactic.getAttackScore(), tactic.getCreativeScore(), tactic.getDefenceScore(), tactic.getFormation(), tactic.getPlayStyle(), tactic.getName());
        return DatabaseConnection.writeQuery("tactic", values);
    }

    public static void updateTactic(Tactic tactic){
        String values = String.format("startingXICode='%s', attackScore='%s', creativeScore='%s', defenceScore='%s', formation='%s', playStyle='%s', name='%s' WHERE tacticCode='%s'", tactic.getStartingXICode(), tactic.getAttackScore(), tactic.getCreativeScore(), tactic.getDefenceScore(), tactic.getFormation(), tactic.getPlayStyle(), tactic.getName(), tactic.getTacticCode());
        updateQuery("tactic", values);
    }

    public static int countTactic(){
        return countQuery("tactic", "tacticCode");
    }
}
