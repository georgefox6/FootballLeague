import java.util.ArrayList;
public class Club {
    //The club class fields
    String name;
    ArrayList<Team> teams;
    Venue venue;

    //Constructors
    Club(){}

    Club(String name, ArrayList<Team> teams, Venue venue){
        this.name = name;
        this.teams = teams;
        this.venue = venue;
    }

    //Getters
    public String getName() {
        return name;
    }
    public ArrayList<Team> getTeams() {
        return teams;
    }
    public Venue getVenue() {
        return venue;
    }
    public String listTeams(){
        String te = "";
        for (int i = 0; i < teams.size(); i++){
            te = te + teams.get(i).getName() + ", ";
        }
        return te;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    public void addTeam(Team team){
        this.teams.add(team);
    }

    public String toString(){
        return "Name : " + name + " Teams : " + this.listTeams() + " Venue : " + venue;
    }

}
