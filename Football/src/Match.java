import java.util.ArrayList;
public class Match {
    //Match variables
    String matchCode;
    Team homeTeam;
    Team awayTeam;
    Venue venue;
    ArrayList<Player> homeStartingXI;
    ArrayList<Player> awayStartingXI;
    int homeGoals;
    int awayGoals;
    String result;
    String date;


    //Constructors
    public Match(){}

    public Match(Team homeTeam, Team awayTeam, ArrayList<Player> homeStartingXI, ArrayList<Player> awayStartingXI, int homeGoals, int awayGoals, String result, String date) {
        this.matchCode = homeTeam.getTeamCode() + "v" + awayTeam.getTeamCode() + date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.venue = homeTeam.getVenue();
        this.homeStartingXI = homeStartingXI;
        this.awayStartingXI = awayStartingXI;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.result = result;
        this.date = date;
    }

    //Getters
    public String getMatchCode() {
        return matchCode;
    }
    public Team getHomeTeam() {
        return homeTeam;
    }
    public Team getAwayTeam() {
        return awayTeam;
    }
    public Venue getVenue() {
        return venue;
    }
    public ArrayList<Player> getHomeStartingXI() {
        return homeStartingXI;
    }
    public ArrayList<Player> getAwayStartingXI() {
        return awayStartingXI;
    }
    public int getHomeGoals() {
        return homeGoals;
    }
    public int getAwayGoals() {
        return awayGoals;
    }
    public String getResult() {
        return result;
    }

    //Setters
    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }
    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    public void setHomeStartingXI(ArrayList<Player> homeStartingXI) {
        this.homeStartingXI = homeStartingXI;
    }
    public void setAwayStartingXI(ArrayList<Player> awayStartingXI) {
        this.awayStartingXI = awayStartingXI;
    }
    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }
    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }
    public void setResult(String result) {
        this.result = result;
    }

    //toString method
    @Override
    public String toString() {
        return "Match{" +
                "matchCode='" + matchCode + '\'' +
                ", homeTeam=" + homeTeam.getName() +
                ", awayTeam=" + awayTeam.getName() +
                ", venue=" + venue.getName() +
                ", homeStartingXI=" + homeStartingXI +
                ", awayStartingXI=" + awayStartingXI +
                ", homeGoals=" + homeGoals +
                ", awayGoals=" + awayGoals +
                ", date=" + date +
                ", result='" + result + '\'' +
                '}';
    }
/*
    //Function to randomly generate num matches
    public static ArrayList<Match> genMatch(int num){
        ArrayList<Match> matches = new ArrayList<Match>();
        for (int i = 0; i < num; i++) {
            ArrayList<Team> ha = Team.genTeam(2);
            Team home = ha.get(0);
            Team away = ha.get(1);
            matches.add(new Match( home, away, home.getStartingXI(), away.getStartingXI(), 1, 2, "Lose", "01012019"));
        }
        return matches;
    }
*/
    public static void main(String[] args) {
//        ArrayList<Match> week1 = genMatch(4);
//        System.out.println(week1.get(0).toString());
//        System.out.println(week1.get(1).toString());
    }
}
