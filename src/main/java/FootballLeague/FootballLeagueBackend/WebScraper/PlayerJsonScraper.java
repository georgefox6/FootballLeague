package FootballLeague.FootballLeagueBackend.WebScraper;

public class PlayerJsonScraper {
    public String name;
    public String dateOfBirth;
    public int age;
    public String position;
    public String nationality;
    public String team;
    public int gamesPlayed;
    public int goalsScored;
    public int assists;
    public int cleanSheets;
    public int goalsConceded;
    public long value;
    public String playerUrl;

    public PlayerJsonScraper(){}

    //Used for all players
    public PlayerJsonScraper(String name, String dateOfBirth, int age, String position, String nationality, String team, int gamesPlayed, int cleanSheets, int goalsConceded, int goalsScored, int assists, long value, String playerUrl) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.position = position;
        this.nationality = nationality;
        this.team = team;
        this.gamesPlayed = gamesPlayed;
        this.cleanSheets = cleanSheets;
        this.goalsConceded = goalsConceded;
        this.goalsScored = goalsScored;
        this.assists = assists;
        this.value = value;
        this.playerUrl = playerUrl;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                ", nationality='" + nationality + '\'' +
                ", team='" + team + '\'' +
                ", gamesPlayed=" + gamesPlayed +
                ", goalsScored=" + goalsScored +
                ", assists=" + assists +
                ", cleanSheets=" + cleanSheets +
                ", goalsConceded=" + goalsConceded +
                ", value=" + value +
                ", playerUrl='" + playerUrl + '\'' +
                '}';
    }
}
