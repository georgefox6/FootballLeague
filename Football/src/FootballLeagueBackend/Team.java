package FootballLeagueBackend;
import java.sql.SQLException;
import java.util.ArrayList;
public class Team {

    //The Team class fields
    String teamCode;
    String name;
    String league;
    String clubCode;
    static int codeIteration;

    static {
        codeIteration = Database.countTeams();
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

    public String getClub() {
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

    public void setClub(String clubCode) {
        this.clubCode = clubCode;
    }


    public void addPlayer(String playerCode)throws SQLException{
        Player player = Database.readPlayer(playerCode);
        player.setTeamCode(this.teamCode);
        Database.updatePlayer(player);
    }

    public void removePlayer(String playerCode)throws SQLException{
        Player player = Database.readPlayer(playerCode);
        player.setTeamCode(null);
        Database.updatePlayer(player);
    }

    //Function to randomly generate num teams
//    public static ArrayList<Team> genTeam(int num){
//        ArrayList<Team> teams = new ArrayList<Team>();
//        for (int i = 0; i < num; i++) {
//            teams.add(new Team(Player.genString(), new Venue(Player.genString(), 500, 5)));
//        }
//        return teams;
//    }


    public static void main(String[] args) {
    }
}
