import java.util.ArrayList;
public class Match {
    //Match variables
    String matchCode;
    String homeTeamCode;
    String awayTeamCode;
    String homeTacticCode;
    String awayTacticCode;
    String score;
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

    public static void main(String[] args) {
//        ArrayList<Match> week1 = genMatch(4);
//        System.out.println(week1.get(0).toString());
//        System.out.println(week1.get(1).toString());
    }
}
