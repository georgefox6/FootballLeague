package FootballLeague.FootballLeagueBackend;

public class LeaguePosition {
    String leaguePositionCode;
    String league;
    int position;
    String teamCode;
    String teamName;
    int played;
    int won;
    int drawn;
    int lost;
    int goalsScored;
    int goalsConceded;
    int goalDifference;
    int points;

    public LeaguePosition(){}

    //Constructor used to initialise the leaguePosition
    public LeaguePosition(String league, int position, String teamCode){
        this.leaguePositionCode = (String.format("%03d", position) + league.charAt(0) + league.charAt(0) + league.charAt(1)).toUpperCase();
        this.league = league;
        this.position = position;
        this.teamCode = teamCode;
        this.teamName = Database.readTeam(teamCode).getName();
        this.played = 0;
        this.won = 0;
        this.drawn = 0;
        this.lost = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
        this.goalDifference = goalsScored - goalsConceded;
        this.points = (won * 3) + (drawn * 1);
    }

    public LeaguePosition(String leaguePositionCode, String league, int position, String teamCode, String teamName, int played, int won, int drawn, int lost, int goalsScored, int goalsConceded, int goalDifference, int points) {
        this.leaguePositionCode = leaguePositionCode;
        this.league = league;
        this.position = position;
        this.teamCode = teamCode;
        this.teamName = teamName;
        this.played = played;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
        this.goalDifference = goalDifference;
        this.points = points;
    }

    public String getLeaguePositionCode() {
        return leaguePositionCode;
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

    public String getTeamName() {
        return teamName;
    }

    public int getPlayed() {
        return played;
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

    public int getGoalDifference() {
        return goalDifference;
    }

    public int getPoints() {
        return points;
    }
}
