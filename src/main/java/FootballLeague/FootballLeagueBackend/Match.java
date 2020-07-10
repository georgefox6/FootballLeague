package FootballLeague.FootballLeagueBackend;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

import FootballLeague.LogHandler.LogHandler;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;
import static FootballLeague.FootballLeagueBackend.LeagueTableEntry.*;

public class Match {

    public static LogHandler log = new LogHandler("FootballLeague.FootballLeagueBackend.Match");

    //Match variables
    String matchCode;
    String homeTeamCode;
    String awayTeamCode;
    String homeTacticCode;
    String awayTacticCode;
    String score;
    // Date is game week
    int date;

    //Constructors
    public Match(){}

    //Constructor used to create complete match
    public Match(String homeTeamCode, String awayTeamCode,String homeTacticCode, String awayTacticCode, String score, int date) {
        this.matchCode = homeTeamCode + "v" + awayTeamCode + date;
        this.homeTeamCode = homeTeamCode;
        this.awayTeamCode = awayTeamCode;
        this.homeTacticCode = homeTacticCode;
        this.awayTacticCode = awayTacticCode;
        this.score = score;
        this.date = date;
    }

    //Constructor used to create future match
    public Match(String homeTeamCode, String awayTeamCode, int date){
        this.matchCode = homeTeamCode + "v" + awayTeamCode + date;
        this.homeTeamCode = homeTeamCode;
        this.awayTeamCode = awayTeamCode;
        this.date = date;
    }

    //Constructor used to create complete match with matchCode for creation of match object from DB so match code remains consistent
    public Match(String matchCode, String homeTeamCode, String awayTeamCode,String homeTacticCode, String awayTacticCode, String score, int date) {
        this.matchCode = matchCode;
        this.homeTeamCode = homeTeamCode;
        this.awayTeamCode = awayTeamCode;
        this.homeTacticCode = homeTacticCode;
        this.awayTacticCode = awayTacticCode;
        this.score = score;
        this.date = date;
    }

    //Constructor used to create future match with matchCode for creation of match object from DB so match code remains consistent
    //Constructor used in Schedule creation
    public Match(String matchCode, String homeTeamCode, String awayTeamCode, int date){
        this.matchCode = matchCode;
        this.homeTeamCode = homeTeamCode;
        this.awayTeamCode = awayTeamCode;
        this.date = date;
    }



    //Getters
    public String getMatchCode() {
        return matchCode;
    }

    public String getHomeTeamCode() {
        return homeTeamCode;
    }

    public String getAwayTeamCode() {
        return awayTeamCode;
    }

    public String getHomeTacticCode() {
        return homeTacticCode;
    }

    public String getAwayTacticCode() {
        return awayTacticCode;
    }

    public String getScore() {
        return score;
    }

    public int getDate() {
        return date;
    }

    public String getHomeTeamName(){
        Team team = Team.readTeam(homeTeamCode);
        return team.getName();
    }

    public String getAwayTeamName(){
        Team team = Team.readTeam(awayTeamCode);
        return team.getName();
    }


    //Setters
    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public void setHomeTeamCode(String homeTeamCode) {
        this.homeTeamCode = homeTeamCode;
    }

    public void setAwayTeamCode(String awayTeamCode) {
        this.awayTeamCode = awayTeamCode;
    }

    public void setHomeTacticCode(String homeTacticCode) {
        this.homeTacticCode = homeTacticCode;
    }

    public void setAwayTacticCode(String awayTacticCode) {
        this.awayTacticCode = awayTacticCode;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setDate(int date) {
        this.date = date;
    }

    //Match Engine method
    public void playMatch(){
        Tactic homeTactic = Tactic.readTactic(this.homeTacticCode);
        Tactic awayTactic = Tactic.readTactic(this.awayTacticCode);

        ///////////////////////////
        //       Home Goals      //
        ///////////////////////////
        Random rand = new Random();

        //Generates the amount of chances the home team will created, this is based on the creativity score of the home team
        //The average in the premier league is between 10 and 20 per game
        double chancesCreated = homeTactic.getCreativeScore() * (2 + rand.nextInt(10)) + rand.nextInt(10) + 5;

        //Reduce the amount of chances the attacking team has based on the defense score of the opposing team
        chancesCreated = chancesCreated - awayTactic.getDefenceScore();

        //Generate the chance conversion rate home team will get (Dependant on the home team attack score) - Average in the premier league is 8% - 22%
        double conversionRate = homeTactic.getAttackScore() * (3 + rand.nextInt(15))/300;
        System.out.println("Conversion: " + round(conversionRate,2));

        int homeGoals = (int)Math.round(chancesCreated * conversionRate);

        ///////////////////////////
        //      Away Goals       //
        ///////////////////////////

        rand = new Random();

        //Generates the amount of chances the away team will created, this is based on the creativity score of the home team
        //The average in the premier league is between 10 and 20 per game
        chancesCreated = awayTactic.getCreativeScore() * (2 + rand.nextInt(10)) + rand.nextInt(10) + 5;

        //Reduce the amount of chances the attacking team has based on the defense score of the opposing team
        chancesCreated = chancesCreated - homeTactic.getDefenceScore();

        //Generate the chance conversion rate away team will get (Dependant on the away team attack score) - Average in the premier league is 8% - 22%
        conversionRate = awayTactic.getAttackScore() * (3 + rand.nextInt(15))/300;

        int awayGoals = (int)Math.round(chancesCreated * conversionRate);

        this.setScore(homeGoals + "-" + awayGoals);

        //Writes the match to the database
        updateMatch(this);

        //These methods are used to update the league table
        LeagueTableEntry homeEntry = readLeagueTableEntryByTeam(this.homeTeamCode);
        LeagueTableEntry awayEntry = readLeagueTableEntryByTeam(this.awayTeamCode);

        //Make changes to the league entry based on the match
        //If the home team wins
        if(homeGoals > awayGoals){
            homeEntry.setWon(homeEntry.getWon() + 1);
            awayEntry.setLost(awayEntry.getLost() + 1);
        }
        //If the away team wins
        else if(homeGoals < awayGoals){
            awayEntry.setWon(awayEntry.getWon() + 1);
            homeEntry.setLost(homeEntry.getLost() + 1);
        }
        //If it is a draw
        else {
            homeEntry.setDrawn(homeEntry.getDrawn() + 1);
            awayEntry.setDrawn(awayEntry.getDrawn() + 1);
        }

        homeEntry.setGoalsScored(homeEntry.getGoalsScored() + homeGoals);
        homeEntry.setGoalsConceded(homeEntry.getGoalsConceded() + awayGoals);

        awayEntry.setGoalsScored(awayEntry.getGoalsScored() + awayGoals);
        awayEntry.setGoalsConceded(awayEntry.getGoalsConceded() + homeGoals);

        //Update the league entry to the database
        updateLeagueTableEntry(homeEntry);
        updateLeagueTableEntry(awayEntry);

        updatePositions();
    }

    public static int getNumWeeks(){
        int max = 0;
        for(Match match : readAllMatches("")){
            if(match.getDate() > max){
                max = match.getDate();
            }
        }
        return max;
    }

    public static Match readMatch(String matchCode){
        ResultSet result = DatabaseConnection.readQuery("matches", "matchCode='" + matchCode);
        try {
            assert result != null;
            if(result.next()){
                return new Match(matchCode, result.getString("homeTeamCode"), result.getString("awayTeamCode"), result.getString("homeTacticCode"), result.getString("awayTacticCode"), result.getString("score"), result.getInt("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return null;
    }

    //For example use "WHERE date='22'" to get matches on the 22nd game week or " " to get all matches
    public static ArrayList<Match> readAllMatches(String clause){
        ArrayList<Match> matches = new ArrayList<>();
        try{
            ResultSet rs = readAllQuery("matches", clause);
            assert rs != null;
            while(rs.next()){
                matches.add(new Match(rs.getString("matchCode"), rs.getString("homeTeamCode"), rs.getString("awayTeamCode"), rs.getString("homeTacticCode"), rs.getString("awayTacticCode"), rs.getString("score"), rs.getInt("date")));
            }
            log.log("Player Size : " + matches.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return matches;
    }

    public static boolean writeMatch(Match match){
        String values = String.format("'%s', '%s', '%s', '%s', '%s', '%s', %d", match.getMatchCode(), match.getHomeTeamCode(), match.getAwayTeamCode(), match.getHomeTacticCode(), match.getAwayTacticCode(), match.getScore(), match.getDate());
        return DatabaseConnection.writeQuery("matches", values);
    }

    public static void updateMatch(Match match){
        String values = String.format("homeTeamCode='%s', awayTeamCode='%s', homeTacticCode='%s', awayTacticCode='%s', score='%s', date=%d WHERE matchCode='%s'", match.getHomeTeamCode(), match.getAwayTeamCode(), match.getHomeTacticCode(), match.getAwayTacticCode(), match.getScore(), match.getDate(), match.getMatchCode());
        updateQuery("matches", values);
    }

    public static int countMatch(){
        return countQuery("match", "matchCode");
    }

    //This method is used round the doubles to a specified number of decimal places
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}


