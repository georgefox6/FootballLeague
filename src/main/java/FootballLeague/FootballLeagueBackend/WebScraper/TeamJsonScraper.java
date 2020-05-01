package FootballLeague.FootballLeagueBackend.WebScraper;

public class TeamJsonScraper {
    public String teamName;
    public String venueName;
    public int venueCapacity;
    public String league;

    public TeamJsonScraper(String teamName, String venueName, int venueCapacity, String league){
        this.teamName = teamName;
        this.venueName = venueName;
        this.venueCapacity = venueCapacity;
        this.league = league;
    }

    public TeamJsonScraper(){}
}
