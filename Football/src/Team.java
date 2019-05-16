import java.lang.reflect.Array;
import java.util.ArrayList;
public class Team {

    //The Team class fields
    String teamCode;
    String name;
    ArrayList<Player> players;
    Venue venue;
    static int codeIteration = 0;

    //Constructors
    Team(){}

    Team(String name, ArrayList<Player> players, Venue venue){
        this.teamCode = String.format("%03d", codeIteration) + name.charAt(0) + name.charAt(1) + name.charAt(2);
        this.name = name;
        this.players = players;
        this.venue = venue;
        codeIteration++;
    }

    //Getters
    public String getTeamCode() {
        return teamCode;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public String listPlayers() {
        String pl = "";
        for (int i = 0; i < players.size(); i++){
            pl = pl + players.get(i).getFullName() + ", ";
        }
        return pl;
    }
    public Venue getVenue() {
        return venue;
    }
    public ArrayList<Player> getStartingXI() {
        return new ArrayList<Player>(players.subList(0,11));
    }

    //Setters
    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public void addPlayer(Player player) {
        this.players.add(player);
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    //toString method
    public String toString(){
        return "Team code : " + teamCode + " Team name : " + name + " Players : " + this.listPlayers();
    }

    //Function to randomly generate num teams
    public static ArrayList<Team> genTeam(int num){
        ArrayList<Team> teams = new ArrayList<Team>();
        for (int i = 0; i < num; i++) {
            teams.add(new Team(Player.genString(), Player.genPlayer(21), new Venue(Player.genString(), 500)));
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
