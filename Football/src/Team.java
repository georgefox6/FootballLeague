import java.sql.SQLException;
import java.util.ArrayList;
public class Team {

    //The Team class fields
    String teamCode;
    String name;
    Venue venue;
    static int codeIteration;

    static {
        codeIteration = Database.countTeams();
    }

    //Constructors
    Team(){}

    Team(String name, Venue venue){
        this.teamCode = (String.format("%03d", codeIteration) + name.charAt(0) + name.charAt(1) + name.charAt(2)).toUpperCase();
        this.name = name;
        this.venue = venue;
        codeIteration++;
    }

    Team(String teamCode, String name, Venue venue){
        this.teamCode = teamCode.toUpperCase();
        this.name = name;
        this.venue = venue;
    }

    //Getters
    public String getTeamCode() {
        return teamCode;
    }
    public String getName() {
        return name;
    }
    //TODO set up a return players from team ( ArrayList )
    //TODO set up a list players from team ( String )
    public String getPlayersString() throws SQLException {
        String pl = "";
        ArrayList<Player> players = Database.readPlayersTeam(this.getTeamCode());
        for (int i = 0; i < players.size(); i++){
            pl = pl + players.get(i).getFullName() + ", ";
        }
        return pl;
    }
    public Venue getVenue() {
        return venue;
    }

    //TODO set up a getStartingXI
//    public ArrayList<Player> getStartingXI() {
//        return new ArrayList<Player>(players.subList(0,11));
//    }

    //Setters
    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }
    public void setName(String name) {
        this.name = name;
    }
//    public void setPlayers(ArrayList<Player> players) {
//        this.players = players;
//    }
//    public void addPlayer(Player player) {
//        this.players.add(player);
//    }
    public void setVenue(Venue venue) {
        this.venue = venue;
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

    //toString method
    public String toString(){
        return "Team code : " + teamCode + " Team name : " + name + " Venue : " + this.getVenue().getName();
    }

    //Function to randomly generate num teams
    public static ArrayList<Team> genTeam(int num){
        ArrayList<Team> teams = new ArrayList<Team>();
        for (int i = 0; i < num; i++) {
            teams.add(new Team(Player.genString(), new Venue(Player.genString(), 500)));
        }
        return teams;
    }


    public static void main(String[] args) {
        ArrayList<Team> league = new ArrayList<Team>();
        league = genTeam(20);
        for(int i=0;i<20;i++){
            System.out.println(league.get(i).toString());
        }
    }
}
