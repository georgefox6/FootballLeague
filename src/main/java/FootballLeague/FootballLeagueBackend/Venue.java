package FootballLeague.FootballLeagueBackend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;

public class Venue {

    public static Logger logger = LogManager.getLogger("com.josh");
    
    //Venue variables
    String venueCode;
    String name;
    int capacity;
    int ticketPrice;
    static int codeIteration;

    static {
        codeIteration = countVenue();
    }

    //Getters
    public String getName() {
        return name;
    }
    public int getCapacity() {
        return capacity;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    //Constructors
    public Venue(){}

    public Venue(String name, int capacity, int ticketPrice){
        codeIteration = countVenue();
        this.venueCode = (String.format("%03d", codeIteration) + name.charAt(0) + name.charAt(1) + name.charAt(2)).toUpperCase();
        this.name = name;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
    }

    public Venue(String venueCode, String name, int capacity, int ticketPrice){
        this.venueCode = venueCode.toUpperCase();
        this.name = name;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
    }

    public static Venue readVenue(String venueCode){
        ResultSet result = DatabaseConnection.readQuery("venue", "venueCode='" + venueCode);
        try {
            assert result != null;
            if(result.next()){
                return new Venue(venueCode, result.getString("venueName"), result.getInt("capacity"), result.getInt("ticketPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return null;
    }

    //For example use "WHERE capacity<8000" to get venues with a capacity of less than 8000 or " " to get all venues
    public static ArrayList<Venue> readAllVenues(String clause){
        ArrayList<Venue> venues = new ArrayList<>();
        try{
            ResultSet rs = readAllQuery("venue", clause);
            assert rs != null;
            while(rs.next()){
                venues.add(new Venue(rs.getString("venueCode"), rs.getString("venueName"), rs.getInt("capacity"), rs.getInt("ticketPrice")));
            }
            logger.info("Venues Size : " + venues.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return venues;
    }

    public static boolean writeVenue(Venue venue){
        String values = String.format("'%s', '%s', '%s', '%s'", venue.getVenueCode(), venue.getName(), venue.getCapacity(), venue.getTicketPrice());
        return DatabaseConnection.writeQuery("venue", values);
    }

    public static void updateVenue(Venue venue){
        String values = String.format("venueName='%s', capacity='%s', ticketPrice='%s' WHERE venueCode='%s'", venue.getName(), venue.getCapacity(), venue.getTicketPrice(), venue.getVenueCode());
        updateQuery("venue", values);
    }

    public static int countVenue(){
        return countQuery("venue", "venueCode");
    }
}
