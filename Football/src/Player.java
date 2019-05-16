import java.util.ArrayList;
import java.util.Random;
public class Player {
    //The player class fields
    String playerCode;
    String forename;
    String surname;
    Boolean injuryStatus;
    static int codeIteration = 0;

    //Constructors
    Player(){};

    Player(String forename, String surname, Boolean injuryStatus){
        this.playerCode = String.format("%03d", codeIteration) + forename.charAt(0) + surname.charAt(0) + surname.charAt(1);
        this.forename = forename;
        this.surname = surname;
        this.injuryStatus = injuryStatus;
        codeIteration++;
    }

    //To String method
    public String toString(){
        return "Player Code : " + playerCode + " Name : " + forename + " " + surname +  " Injured? " + injuryStatus;
    }

    //Setters
    public void setPlayerCode(String playerCode){
        this.playerCode = playerCode;
    }
    public void setForename(String forename){
        this.forename = forename;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public void setInjuryStatus(Boolean injuryStatus){
        this.injuryStatus = injuryStatus;
    }

    //Getters
    public String getPlayerCode() {
        return playerCode;
    }
    public String getForename() {
        return forename;
    }
    public String getSurname() {
        return surname;
    }
    public Boolean getInjuryStatus() {
        return injuryStatus;
    }

    public String getFullName() {
        return forename + " " + surname;
    }

    public static String genString(){
        String alph = "ABCDEFGHIJKLMNOPQRSTVWXYZ";
        String curString = "";
        Random rand = new Random();
        int length = rand.nextInt(5) + 5;
        for (int i = 0; i < length; i++){
            int charNum = rand.nextInt(25);
            curString = curString + alph.charAt(charNum);
        }
        return curString;
    }
    public static String genStringInt(){
        String num = "0123456789";
        String curStringInt = "";
        Random rand = new Random();
        int length = rand.nextInt(4) + 4;
        for (int i = 0; i < length; i++){
            int charNum = rand.nextInt(9);
            curStringInt = curStringInt + num.charAt(charNum);
        }
        return curStringInt;
    }

    public static ArrayList<Player> genPlayer(int num){
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < num; i++) {
            players.add(new Player(genString(), genString(), false));
        }
        return players;
    }

    //Main method
    public static void main(String[] args){
        for(int i=0;i<9;i++) {
            System.out.println(genPlayer(10).get(i).getPlayerCode());
        }
    }
}
