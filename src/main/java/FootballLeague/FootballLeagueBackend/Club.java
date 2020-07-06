package FootballLeague.FootballLeagueBackend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;  

import FootballLeague.LogHandler.LogHandler;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;

public class Club {

    public static LogHandler log = new LogHandler("FootballLeague.FootballLeagueBackend.Club");

    //The club class fields
    String clubCode;
    String name;
    String venueCode;

    static int codeIteration;

    static {
        codeIteration = countClub();
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

    public static Club readClub(String clubCode){
        ResultSet result = DatabaseConnection.readQuery("club", "clubCode='" + clubCode);
        try {
            assert result != null;
            if(result.next()){
                return new Club(clubCode, result.getString("clubName"), result.getString("venue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return null;
    }

    //For example use "WHERE venue='Old Trafford'" to get clubs that play at old Trafford or " " to get all Clubs
    public static ArrayList<Club> readAllClubs(String clause){
        ArrayList<Club> clubs = new ArrayList<>();
        try{
            ResultSet rs = readAllQuery("club", clause);
            assert rs != null;
            while(rs.next()){
                clubs.add(new Club(rs.getString("clubCode"), rs.getString("clubName"), rs.getString("venue")));
            }
            log.log("Club Size : " + clubs.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return clubs;
    }

    public static boolean writeClub(Club club){
        String values = String.format("'%s', '%s', '%s'", club.getClubCode(), club.getName(), club.getVenue());
        return DatabaseConnection.writeQuery("club", values);
    }

    public static void updateClub(Club club){
        String values = String.format("clubName='%s', venue='%s' WHERE clubCode='%s'", club.getName(), club.getVenue(), club.getClubCode());
        updateQuery("club", values);
    }

    public static int countClub(){
        return countQuery("club", "clubCode");
    }
}
