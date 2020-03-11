package FootballLeague.FootballLeagueBackend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;

public class Match {
    //Match variables
    String matchCode;
    String homeTeamCode;
    String awayTeamCode;
    String homeTacticCode;
    String awayTacticCode;
    String score;
    // Date is gameweek
    String date;

    //Constructors
    public Match(){}

    //Constructor used to create complete match
    public Match(String homeTeamCode, String awayTeamCode,String homeTacticCode, String awayTacticCode, String score, String date) {
        this.matchCode = homeTeamCode + "v" + awayTeamCode + date;
        this.homeTeamCode = homeTeamCode;
        this.awayTeamCode = awayTeamCode;
        this.homeTacticCode = homeTacticCode;
        this.awayTacticCode = awayTacticCode;
        this.score = score;
        this.date = date;
    }

    //Constructor used to create future match
    public Match(String homeTeamCode, String awayTeamCode, String date){
        this.matchCode = homeTeamCode + "v" + awayTeamCode + date;
        this.homeTeamCode = homeTeamCode;
        this.awayTeamCode = awayTeamCode;
        this.date = date;
    }

    //Constructor used to create complete match with matchCode for creation of match object from DB so match code remains consistent
    public Match(String matchCode, String homeTeamCode, String awayTeamCode,String homeTacticCode, String awayTacticCode, String score, String date) {
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
    public Match(String matchCode, String homeTeamCode, String awayTeamCode, String date){
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

    public String getDate() {
        return date;
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

    public void setDate(String date) {
        this.date = date;
    }

    //TODO Test the playMatch method
    //Match Engine method
    public void playMatch(){
        Tactic homeTactic = Database.readTactic(this.homeTacticCode);
        Tactic awayTactic = Database.readTactic(this.awayTacticCode);

        //                      HOME TEAM GOALS
        //Generate amount of chances home team will get (Dependant on the opponents defense) - Average in the premier league is 10 to 20 per game
        Random rand = new Random();
        double chancesCreated = Math.round(rand.nextInt(40) * (1 - awayTactic.getDefenceScore()));
        //Generate conversion rate home team will get (Dependant on the home team attack score) - Average in the premier league is 8% - 22%
        rand = new Random();
        double conversionRate = homeTactic.getAttackScore() * (rand.nextInt(100)/200);
        //The final number of goals scored by the home team
        int homeGoals = (int)Math.round(chancesCreated * conversionRate);

        //                      AWAY TEAM GOALS
        //Generate amount of chances away team will get (Dependant on the opponents defense) - Average in the premier league is 10 to 20 per game
        rand = new Random();
        chancesCreated = Math.round(rand.nextInt(40) * (1 - homeTactic.getDefenceScore()));
        //Generate conversion rate away team will get (Dependant on the away team attack score) - Average in the premier league is 8% - 22%
        rand = new Random();
        conversionRate = awayTactic.getAttackScore() * (rand.nextInt(100)/200);
        //The final number of goals scored by the away team
        int awayGoals = (int)Math.round(chancesCreated * conversionRate);

        this.setScore(homeGoals + "-" + awayGoals);

        //These functions are used to update the league table for both the home and away teams
        Database.updateLeagueTableHome(this);
        Database.updateLeagueTableAway(this);
    }

    //TODO add function to organise the scheduling of matches (Create every match and set dates)

    public static Match readMatch(String matchCode){
        ResultSet result = DatabaseConnection.readQuery("matches", "matchCode='" + matchCode);
        try {
            assert result != null;
            if(result.next()){
                return new Match(matchCode, result.getString("homeTeamCode"), result.getString("awayTeamCode"), result.getString("homeTacticCode"), result.getString("awayTacticCode"), result.getString("score"), result.getString("date"));
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
                matches.add(new Match(rs.getString("matchCode"), rs.getString("homeTeamCode"), rs.getString("awayTeamCode"), rs.getString("homeTacticCode"), rs.getString("awayTacticCode"), rs.getString("score"), rs.getString("date")));
            }
            System.out.println("Player Size : " + matches.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return matches;
    }

    public static boolean writeMatch(Match match){
        String values = String.format("'%s', '%s', '%s', '%s', '%s', '%s', '%s'", match.getMatchCode(), match.getHomeTeamCode(), match.getAwayTeamCode(), match.getHomeTacticCode(), match.getAwayTacticCode(), match.getScore(), match.getDate());
        return DatabaseConnection.writeQuery("matches", values);
    }

    public static void updateMatch(Match match){
        String values = String.format("homeTeamCode='%s', awayTeamCode='%s', homeTacticCode='%s', awayTacticCode='%s', score='%s', date='%s' WHERE matchCode='%s'", match.getHomeTeamCode(), match.getAwayTeamCode(), match.getHomeTacticCode(), match.getAwayTacticCode(), match.getScore(), match.getDate(), match.getMatchCode());
        updateQuery("matches", values);
    }

    public static int countMatch(){
        return countQuery("match", "matchCode");
    }
}
