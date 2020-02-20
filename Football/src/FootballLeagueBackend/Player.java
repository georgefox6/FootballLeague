package FootballLeagueBackend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static FootballLeagueBackend.DatabaseConnection.*;

public class Player {
    //The player class fields
    String playerCode;
    String forename;
    String surname;
    Boolean injuryStatus;
    String teamCode;
    static int codeIteration;

    //This is used to auto increment the player code so that they are all unique
    static {
        codeIteration = countPlayer();
    }

    //Constructors
    public Player(){}

    //Constructor without team teamCode and generated player code
    public Player(String forename, String surname, Boolean injuryStatus){
        this.playerCode = (String.format("%03d", codeIteration) + forename.charAt(0) + surname.charAt(0) + surname.charAt(1)).toUpperCase();
        this.forename = forename;
        this.surname = surname;
        this.injuryStatus = injuryStatus;
        this.teamCode = null;
    }

    //Constructor with teamCode and generated player code
    public Player(String forename, String surname, Boolean injuryStatus, String teamCode){
        this.playerCode = (String.format("%03d", codeIteration) + forename.charAt(0) + surname.charAt(0) + surname.charAt(1)).toUpperCase();
        this.forename = forename;
        this.surname = surname;
        this.injuryStatus = injuryStatus;
        this.teamCode = teamCode;
        codeIteration++;
    }

    //Constructor with playerCode for creation of player object from DB so player code remains consistent
    public Player(String playerCode, String forename, String surname, Boolean injuryStatus, String teamCode){
        this.playerCode = playerCode.toUpperCase();
        this.forename = forename;
        this.surname = surname;
        this.injuryStatus = injuryStatus;
        this.teamCode = teamCode;
    }

    //To String method
    public String toString(){
        return forename.charAt(0) + " " + surname;
    }

    //Setters
    public void setForename(String forename){
        this.forename = forename;
        updatePlayer(this);
    }
    public void setSurname(String surname){
        this.surname = surname;
        updatePlayer(this);
    }
    public void setInjuryStatus(Boolean injuryStatus){
        this.injuryStatus = injuryStatus;
        updatePlayer(this);
    }
    public void setTeamCode(String teamCode){
        this.teamCode = teamCode;
        updatePlayer(this);
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
    public Boolean getInjuryStatus() {
        return injuryStatus;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public String getFullName() {
        return forename + " " + surname;
    }

    public static Player readPlayer(String playerCode){
        ResultSet result = DatabaseConnection.readQuery("player", "playerCode='" + playerCode);
            try {
                assert result != null;
                if(result.next()){
                    return new Player(playerCode, result.getString("forename"), result.getString("surname"), result.getString("injuryStatus").equals("true"), result.getString("teamCode"));
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
                players.add(new Player(rs.getString("playerCode"), rs.getString("forename"), rs.getString("surname"), rs.getString("injuryStatus").equals("true"), rs.getString("teamCode")));
            }
            System.out.println("Player Size : " + players.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return players;
    }

    public static boolean writePlayer(Player player){
        String values = String.format("'%s', '%s', '%s', '%s', '%s'", player.getPlayerCode(), player.getForename(), player.getSurname(), player.getInjuryStatus(), player.getTeamCode());
        return DatabaseConnection.writeQuery("player", values);
    }

    public static boolean updatePlayer(Player player){
        String values = String.format("forename='%s', surname='%s', injuryStatus='%s', teamCode='%s' WHERE playerCode='%s'", player.getForename(), player.getSurname(), player.getInjuryStatus(), player.getTeamCode(), player.getPlayerCode());
        return updateQuery("player", values);
    }

    public static int countPlayer(){
        return countQuery("player", "playerCode");
    }
}
