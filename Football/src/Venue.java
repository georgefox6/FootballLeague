public class Venue {
    //Venue variables
    //TODO ADD venue code constructor and generator
    String venueCode;
    String name;
    int capacity;

    //Constructors
    Venue(){}

    Venue(String name, int capactity){
        this.name = name;
        this.capacity = capactity;
    }

    //Getters
    public String getName() {
        return name;
    }
    public int getCapacity() {
        return capacity;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String toString(){
        return "Name : " + name + " Capacity : " + capacity;
    }
}
