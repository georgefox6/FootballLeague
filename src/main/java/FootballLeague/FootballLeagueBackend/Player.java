package FootballLeague.FootballLeagueBackend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;

public class Player implements Comparable<Player>{

    public static Logger logger = LogManager.getLogger("com.josh");

    //The player class fields
    String playerCode;
    String forename;
    String surname;
    String teamCode;
    double attackingStat;
    double creativityStat;
    double defensiveStat;
    double overallStat;
    String position;
    String role;
    int value;

    static int codeIteration;

    //This is used to auto increment the player code so that they are all unique
    static {
        codeIteration = countPlayer();
    }

    //Constructors
    public Player(){}

    //Constructor without team teamCode and generated player code
    public Player(String forename, String surname, double attackingStat, double creativityStat, double defensiveStat, String position, String role, int value){
        this.playerCode = (String.format("%03d", codeIteration) + forename.charAt(0) + surname.charAt(0) + surname.charAt(1)).toUpperCase();
        this.forename = forename;
        this.surname = surname;
        this.attackingStat = attackingStat;
        this.creativityStat = creativityStat;
        this.defensiveStat = defensiveStat;
        this.teamCode = null;
        this.position = position;
        this.role = role;
        this.value = value;
        this.overallStat = attackingStat + creativityStat + defensiveStat;
    }

    //Constructor with teamCode and generated player code
    public Player(String forename, String surname, double attackingStat, double creativityStat, double defensiveStat, String teamCode, String position, String role, int value){
        this.playerCode = (String.format("%03d", codeIteration) + forename.charAt(0) + surname.charAt(0) + surname.charAt(1)).toUpperCase();
        this.forename = forename;
        this.surname = surname;
        this.attackingStat = attackingStat;
        this.creativityStat = creativityStat;
        this.defensiveStat = defensiveStat;
        this.teamCode = teamCode;
        codeIteration++;
        this.position = position;
        this.role = role;
        this.value = value;
        this.overallStat = attackingStat + creativityStat + defensiveStat;
    }

    //Constructor with playerCode for creation of player object from DB so player code remains consistent
    public Player(String playerCode, String forename, String surname, double attackingStat, double creativityStat, double defensiveStat, String teamCode, String position, String role, int value){
        this.playerCode = playerCode.toUpperCase();
        this.forename = forename;
        this.surname = surname;
        this.attackingStat = attackingStat;
        this.creativityStat = creativityStat;
        this.defensiveStat = defensiveStat;
        this.teamCode = teamCode;
        this.position = position;
        this.role = role;
        this.value = value;
        this.overallStat = attackingStat + creativityStat + defensiveStat;
    }

    //To String method
    public String toString(){
        return forename.charAt(0) + " " + surname;
    }

    //Setters
    public void setForename(String forename){
        this.forename = forename;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public void setAttackingStat(double attackingStat) {
        this.attackingStat = attackingStat;
    }

    public void setCreativityStat(double creativityStat) {
        this.creativityStat = creativityStat;
    }

    public void setDefensiveStat(double defensiveStat) {
        this.defensiveStat = defensiveStat;
    }

    public void setTeamCode(String teamCode){
        this.teamCode = teamCode;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setValue(int value) {
        this.value = value;
    }

    //Getters
    public String getPlayerCode() {
        return playerCode;
    }
    public String getForename() {
        return forename;
    }
    public String getSurname() {
        return surname;
    }

    public double getAttackingStat() {
        return attackingStat;
    }

    public double getCreativityStat() {
        return creativityStat;
    }

    public double getDefensiveStat() {
        return defensiveStat;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public String getFullName() {
        return forename + " " + surname;
    }

    public double getOverallStat() {
        return overallStat;
    }

    public String getTeamName(){
        Team team = Team.readTeam(teamCode);
        if(team == null){
            return "Free Agent";
        } else {
            return team.getName();
        }
    }

    public String getPosition() {
        return position;
    }

    public String getRole() {
        return role;
    }

    public int getValue() {
        return value;
    }

    //Used to order player by overall ability
    @Override
    public int compareTo(Player o) {
        if(this.getOverallStat() == o.getOverallStat()){
            if(this.getValue() < o.getValue()){
                return 1;
            }
        }
        if(this.getOverallStat() < o.getOverallStat()){
            return 1;
        } else {
            return -1;
        }
    }

    public static Player readPlayer(String playerCode){
        ResultSet result = DatabaseConnection.readQuery("player", "playerCode='" + playerCode);
            try {
                assert result != null;
                if(result.next()){
                    return new Player(playerCode, result.getString("forename"), result.getString("surname"), result.getDouble("attackingStat"), result.getDouble("creativityStat"), result.getDouble("defensiveStat"), result.getString("teamCode"), result.getString("position"), result.getString("role"), result.getInt("value"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseConnection.close();
            }
        return null;
    }

    //For example use "WHERE teamCode='006SPU'" to get players from 006SPU or " " to get all players
    public static ArrayList<Player> readAllPlayers(String clause){
        ArrayList<Player> players = new ArrayList<>();
        try{
            ResultSet rs = readAllQuery("player", clause);
            assert rs != null;
            while(rs.next()){
                players.add(new Player(rs.getString("playerCode"), rs.getString("forename"), rs.getString("surname"), rs.getDouble("attackingStat"), rs.getDouble("creativityStat"), rs.getDouble("defensiveStat"), rs.getString("teamCode"), rs.getString("position"), rs.getString("role"), rs.getInt("value")));
            }
            logger.info("Player Size : " + players.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return players;
    }

    public static boolean writePlayer(Player player){
        String values = String.format("'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'", player.getPlayerCode(), player.getForename(), player.getSurname(), player.getAttackingStat(), player.getCreativityStat(), player.getDefensiveStat(), player.getTeamCode(), player.getPosition(), player.getRole(), player.getValue());
        return DatabaseConnection.writeQuery("player", values);
    }

    public static void updatePlayer(Player player){
        String values = String.format("forename='%s', surname='%s', attackingStat='%s', creativityStat='%s', defensiveStat='%s', teamCode='%s', position='%s', role='%s', value='%s' WHERE playerCode='%s'", player.getForename(), player.getSurname(), player.getAttackingStat(), player.getCreativityStat(), player.getDefensiveStat(), player.getTeamCode(), player.getPosition(), player.getRole(), player.getValue(), player.getPlayerCode());
        updateQuery("player", values);
    }

    public static int countPlayer(){
        return countQuery("player", "playerCode");
    }
}
