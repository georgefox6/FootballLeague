package FootballLeague.FootballLeagueBackend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;
import static FootballLeague.FootballLeagueBackend.Player.readPlayer;
import static FootballLeague.FootballLeagueBackend.Player.updatePlayer;

public class Team {

    //The Team class fields
    String teamCode;
    String name;
    String league;
    String clubCode;
    static int codeIteration;

    static {
        codeIteration = countTeam();
    }

    //Constructors
    public Team(){}

    public Team(String name, String league, String clubCode){
        this.teamCode = (String.format("%03d", codeIteration) + name.charAt(0) + name.charAt(1) + name.charAt(2)).toUpperCase();
        this.name = name;
        this.league = league;
        this.clubCode = clubCode;
        codeIteration++;
    }

    public Team(String teamCode, String name, String league, String clubCode){
        this.teamCode = teamCode.toUpperCase();
        this.name = name;
        this.league = league;
        this.clubCode = clubCode;
    }

    //To string method


    @Override
    public String toString() {
        return this.name;
    }

    //Getters
    public String getTeamCode() {
        return teamCode;
    }
    public String getName() {
        return name;
    }

    public String getLeague() {
        return league;
    }

    public String getClubCode() {
        return clubCode;
    }

    //Setters
    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setClubCode(String clubCode) {
        this.clubCode = clubCode;
    }


    public void addPlayer(String playerCode) {
        Player player = readPlayer(playerCode);
        player.setTeamCode(this.teamCode);
        updatePlayer(player);
    }

    public void removePlayer(String playerCode) {
        Player player = readPlayer(playerCode);
        player.setTeamCode(null);
        updatePlayer(player);
    }

    /* Database operations */

    public static Team readTeam(String teamCode){
        ResultSet result = DatabaseConnection.readQuery("team", "teamCode='" + teamCode);
        try {
            assert result != null;
            if(result.next()){
                return new Team(teamCode, result.getString("teamName"), result.getString("league"), result.getString("club"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return null;
    }

    //For example use "WHERE club='Watford'" to get teams from Watford or " " to get all teams
    public static ArrayList<Team> readAllTeams(String clause){
        ArrayList<Team> teams = new ArrayList<>();
        try{
            ResultSet rs = readAllQuery("team", clause);
            assert rs != null;
            while(rs.next()){
                teams.add(new Team(rs.getString("teamCode"), rs.getString("teamName"), rs.getString("league"), rs.getString("club")));
            }
            System.out.println("Player Size : " + teams.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return teams;
    }

    public static boolean writeTeam(Team team){
        String values = String.format("'%s', '%s', '%s', '%s'", team.getTeamCode(), team.getName(), team.getLeague(), team.getClubCode());
        return DatabaseConnection.writeQuery("team", values);
    }

    public static void updateTeam(Team team){
        String values = String.format("teamName='%s', league='%s', club='%s' WHERE teamCode='%s'", team.getName(), team.getLeague(), team.getClubCode(), team.getTeamCode());
        updateQuery("team", values);
    }

    public static int countTeam(){
        return countQuery("team", "teamCode");
    }


    //For example use "WHERE club='Watford'" to get teams from Watford or " " to get all teams
    //The difference between this and readAllTeams is that this reads from the default database
    public static ArrayList<Team> readAllTeamsMain(String clause){
        ArrayList<Team> teams = new ArrayList<>();
        try{
            ResultSet rs = readAllFromMainQuery("team", clause);
            assert rs != null;
            while(rs.next()){
                teams.add(new Team(rs.getString("teamCode"), rs.getString("teamName"), rs.getString("league"), rs.getString("club")));
            }
            System.out.println("Player Size : " + teams.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return teams;
    }
}
