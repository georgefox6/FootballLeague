package FootballLeagueBackend;

public class Club {
    //The club class fields
    String clubCode;
    String name;
    Venue venue;

    static int codeIteration;

    static {
        codeIteration = Database.countClubs();
    }

    //Constructors
    Club(){}

    Club(String clubCode, String name, Venue venue){
        this.clubCode = clubCode.toUpperCase();
        this.name = name;
        this.venue = venue;
    }
    Club(String name, Venue venue){
        this.clubCode = (String.format("%03d", codeIteration) + name.charAt(0) + name.charAt(1) + name.charAt(2)).toUpperCase();
        this.name = name;
        this.venue = venue;
        codeIteration++;
    }

    //Getters

    public String getClubCode() {
        return clubCode;
    }

    public String getName() {
        return name;
    }

    public Venue getVenue() {
        return venue;
    }

    //Setters

    public void setClubCode(String clubCode) {
        this.clubCode = clubCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

}
