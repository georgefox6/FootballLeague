package FootballLeague.FootballLeagueBackend;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;
import static FootballLeague.FootballLeagueBackend.GameState.readTeam;
import static FootballLeague.FootballLeagueBackend.Team.readAllTeams;

public class LeagueTableEntry implements Comparable<LeagueTableEntry> {

    //leagueTableEntryCode is teamCode+league
    String leagueTableEntryCode;
    String league;
    int position;
    String teamCode;
    int won;
    int drawn;
    int lost;
    int goalsScored;
    int goalsConceded;

    public LeagueTableEntry(){
    }

    public LeagueTableEntry(String league, int position, String teamCode, int won, int drawn, int lost, int goalsScored, int goalsConceded){
        this.leagueTableEntryCode = teamCode + league;
        this.league = league;
        this.position = position;
        this.teamCode = teamCode;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
    }

    public LeagueTableEntry(String leagueTableEntryCode, String league, int position, String teamCode, int won, int drawn, int lost, int goalsScored, int goalsConceded){
        this.leagueTableEntryCode = leagueTableEntryCode;
        this.league = league;
        this.position = position;
        this.teamCode = teamCode;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
    }

    public String getLeagueTableEntryCode() {
        return leagueTableEntryCode;
    }

    public String getLeague() {
        return league;
    }

    public int getPosition() {
        return position;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public int getWon() {
        return won;
    }

    public int getDrawn() {
        return drawn;
    }

    public int getLost() {
        return lost;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public int getPoints(){
        return won * 3 + drawn;
    }

    public int getPlayed(){
        return won + drawn + lost;
    }

    public int getGoalDifference(){
        return goalsScored - goalsConceded;
    }

    public String getTeamName(){
        Team team = Team.readTeam(teamCode);
        return team.getName();
    }

    public void setLeagueTableEntryCode(String leagueTableEntryCode) {
        this.leagueTableEntryCode = leagueTableEntryCode;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    //The init method will set up all of the league tables with initial values
    public static void initLeagueTable(){
        ArrayList<String> uniqueLeagues = readUniqueLeagues();

        //for each league, read all teams
        for(String league : uniqueLeagues){

            //for each team create a new league table entry
            int position = 1;
            for(Team team : readAllTeams("WHERE league='" + league + "';")){
                LeagueTableEntry temp = new LeagueTableEntry(league, position, team.getTeamCode(), 0, 0, 0, 0, 0);
                writeLeagueTableEntry(temp);
                position++;
            }
        }
    }

    public static void updatePositions(){
        for(String league : readUniqueLeagues()) {
            ArrayList<LeagueTableEntry> allEntriesInLeague = readAllLeagueTableEntries("WHERE league='" + league + "';");
            Collections.sort(allEntriesInLeague);
            int position = 1;
            for (LeagueTableEntry entry : allEntriesInLeague) {
                entry.setPosition(position);
                updateLeagueTableEntry(entry);
                position++;
            }
        }
    }

    @Override
    public int compareTo(LeagueTableEntry o) {
        if(o.getPoints() == this.getPoints()){
            return o.getGoalDifference() - this.getGoalDifference();
        }
        return (o.getPoints() - this.getPoints());
    }

    public static LeagueTableEntry readLeagueTableEntry(String LeagueTableEntryCode){
        ResultSet result = DatabaseConnection.readQuery("LeagueTableEntry", "LeagueTableEntryCode='" + LeagueTableEntryCode);
        try {
            assert result != null;
            if(result.next()){
                return new LeagueTableEntry(LeagueTableEntryCode, result.getString("league"), result.getInt("position"), result.getString("teamCode"), result.getInt("won"), result.getInt("drawn"), result.getInt("lost"), result.getInt("goalsScored"), result.getInt("goalsConceded"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return null;
    }

    public static ArrayList<String> readUniqueLeagues(){
        //get all of the different leagues from the leagueTableEntry table and store them in uniqueLeagues
        ArrayList<String> uniqueLeagues = new ArrayList<>();

        for(Team t :  readAllTeams("")){
            if(!uniqueLeagues.contains(t.getLeague())){
                uniqueLeagues.add(t.getLeague());
            }
        }
        return uniqueLeagues;
    }

    public static LeagueTableEntry readLeagueTableEntryByTeam(String teamCode){
        ResultSet result = DatabaseConnection.readQuery("LeagueTableEntry", "teamCode='" + teamCode);
        try {
            assert result != null;
            if(result.next()){
                return new LeagueTableEntry(result.getString("LeagueTableEntryCode"), result.getString("league"), result.getInt("position"), result.getString("teamCode"), result.getInt("won"), result.getInt("drawn"), result.getInt("lost"), result.getInt("goalsScored"), result.getInt("goalsConceded"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return null;
    }

    //For example use "WHERE league='Premier League'" to get league table entries from the Premier League or " " to get all League Entries
    public static ArrayList<LeagueTableEntry> readAllLeagueTableEntries(String clause){
        ArrayList<LeagueTableEntry> leagueTableEntries = new ArrayList<>();
        try{
            ResultSet result = readAllQuery("LeagueTableEntry", clause);
            assert result != null;
            while(result.next()){
                leagueTableEntries.add(new LeagueTableEntry(result.getString("leagueTableEntryCode"), result.getString("league"), result.getInt("position"), result.getString("teamCode"), result.getInt("won"), result.getInt("drawn"), result.getInt("lost"), result.getInt("goalsScored"), result.getInt("goalsConceded")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return leagueTableEntries;
    }

    public static boolean writeLeagueTableEntry(LeagueTableEntry leagueTableEntry){
        String values = String.format("'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'", leagueTableEntry.getLeagueTableEntryCode(), leagueTableEntry.getLeague(), leagueTableEntry.getPosition(), leagueTableEntry.getTeamCode(), leagueTableEntry.getWon(), leagueTableEntry.getDrawn(), leagueTableEntry.getLost(), leagueTableEntry.getGoalsScored(), leagueTableEntry.getGoalsConceded());
        return DatabaseConnection.writeQuery("leagueTableEntry", values);
    }

    public static void updateLeagueTableEntry(LeagueTableEntry leagueTableEntry){
        String values = String.format("league='%s', position='%s', teamCode='%s', won='%s', drawn='%s', lost='%s', goalsScored='%s', goalsConceded='%s' WHERE leagueTableEntryCode='%s'", leagueTableEntry.getLeague(), leagueTableEntry.getPosition(), leagueTableEntry.getTeamCode(), leagueTableEntry.getWon(), leagueTableEntry.getDrawn(), leagueTableEntry.getLost(), leagueTableEntry.getGoalsScored(), leagueTableEntry.getGoalsConceded(), leagueTableEntry.getLeagueTableEntryCode());
        updateQuery("leagueTableEntry", values);
    }

    public static int countVenue(){
        return countQuery("leagueTableEntry", "leagueTableEntryCode");
    }

}
