package FootballLeagueBackend;
import java.util.ArrayList;
public class Club {
    //The club class fields
    String clubCode;
    String name;
    String venueCode;

    static int codeIteration;

    static {
        codeIteration = Database.countClubs();
    }

    //Constructors
    public Club(){}

    public Club(String clubCode, String name, String venueCode){
        this.clubCode = clubCode.toUpperCase();
        this.name = name;
        this.venueCode = venueCode;
    }
    public Club(String name, String venueCode){
        this.clubCode = (String.format("%03d", codeIteration) + name.charAt(0) + name.charAt(1) + name.charAt(2)).toUpperCase();
        this.name = name;
        this.venueCode = venueCode;
        codeIteration++;
    }

    //Getters

    public String getClubCode() {
        return clubCode;
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venueCode;
    }

    //Setters

    public void setClubCode(String clubCode) {
        this.clubCode = clubCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVenue(String venueCode) {
        this.venueCode = venueCode;
    }

}
