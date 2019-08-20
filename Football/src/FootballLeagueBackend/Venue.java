package FootballLeagueBackend;

public class Venue {
    //Venue variables
    String venueCode;
    String name;
    int capacity;
    int ticketPrice;
    static int codeIteration;

    static {
        codeIteration = Database.countVenues();
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
    Venue(){}

    Venue(String name, int capacity, int ticketPrice){
        this.venueCode = (String.format("%03d", codeIteration) + name.charAt(0) + name.charAt(1) + name.charAt(2)).toUpperCase();
        this.name = name;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
    }

    Venue(String venueCode, String name, int capacity, int ticketPrice){
        this.venueCode = venueCode.toUpperCase();
        this.name = name;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
    }



}
