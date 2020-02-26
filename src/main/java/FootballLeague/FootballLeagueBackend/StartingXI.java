package FootballLeague.FootballLeagueBackend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static FootballLeague.FootballLeagueBackend.DatabaseConnection.*;

public class StartingXI {
    public String startingXICode;
    public String player1;
    public String player2;
    public String player3;
    public String player4;
    public String player5;
    public String player6;
    public String player7;
    public String player8;
    public String player9;
    public String player10;
    public String player11;

    static int codeIteration;

    static {
        codeIteration = countStartingXI();
    }

    public StartingXI(){
    }

    public StartingXI(String startingXICode, String player1, String player2, String player3, String player4, String player5, String player6, String player7, String player8, String player9, String player10, String player11) {
        this.startingXICode = startingXICode;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.player5 = player5;
        this.player6 = player6;
        this.player7 = player7;
        this.player8 = player8;
        this.player9 = player9;
        this.player10 = player10;
        this.player11 = player11;
    }

    public StartingXI(String player1, String player2, String player3, String player4, String player5, String player6, String player7, String player8, String player9, String player10, String player11) {
        this.startingXICode = (String.format("%03d", codeIteration) + player1.charAt(3) + player2.charAt(4) + player3.charAt(5)).toUpperCase();
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.player5 = player5;
        this.player6 = player6;
        this.player7 = player7;
        this.player8 = player8;
        this.player9 = player9;
        this.player10 = player10;
        this.player11 = player11;
        codeIteration++;
    }

    //Getters and setters
    public String getStartingXICode() {
        return startingXICode;
    }

    public void setStartingXICode(String startingXICode) {
        this.startingXICode = startingXICode;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public String getPlayer4() {
        return player4;
    }

    public void setPlayer4(String player4) {
        this.player4 = player4;
    }

    public String getPlayer5() {
        return player5;
    }

    public void setPlayer5(String player5) {
        this.player5 = player5;
    }

    public String getPlayer6() {
        return player6;
    }

    public void setPlayer6(String player6) {
        this.player6 = player6;
    }

    public String getPlayer7() {
        return player7;
    }

    public void setPlayer7(String player7) {
        this.player7 = player7;
    }

    public String getPlayer8() {
        return player8;
    }

    public void setPlayer8(String player8) {
        this.player8 = player8;
    }

    public String getPlayer9() {
        return player9;
    }

    public void setPlayer9(String player9) {
        this.player9 = player9;
    }

    public String getPlayer10() {
        return player10;
    }

    public void setPlayer10(String player10) {
        this.player10 = player10;
    }

    public String getPlayer11() {
        return player11;
    }

    public void setPlayer11(String player11) {
        this.player11 = player11;
    }

    public static StartingXI readStartingXI(String startingXICode){
        ResultSet result = DatabaseConnection.readQuery("startingXI", "startingXICode='" + startingXICode);
        try {
            assert result != null;
            if(result.next()){
                return new StartingXI(startingXICode, result.getString("player1"), result.getString("player2"), result.getString("player3"), result.getString("player4"), result.getString("player5"), result.getString("player6"), result.getString("player7"), result.getString("player8"), result.getString("player9"), result.getString("player10"), result.getString("player11"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return null;
    }

    //For example use "WHERE player1='001GFO'" to get startingXI's where the goalkeeper is 001GFO or " " to get all StartingXI's
    public static ArrayList<StartingXI> readAllStartingXIs(String clause){
        ArrayList<StartingXI> StartingXIs = new ArrayList<>();
        try{
            ResultSet rs = readAllQuery("startingXI", clause);
            assert rs != null;
            while(rs.next()){
                StartingXIs.add(new StartingXI(rs.getString("startingXICode"), rs.getString("player1"), rs.getString("player2"), rs.getString("player3"), rs.getString("player4"), rs.getString("player5"), rs.getString("player6"), rs.getString("player7"), rs.getString("player8"), rs.getString("player9"), rs.getString("player10"), rs.getString("player11")));
            }
            System.out.println("StartingXIs Size : " + StartingXIs.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close();
        }
        return StartingXIs;
    }

    public static boolean writeStartingXI(StartingXI startingXI){
        String values = String.format("'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'", startingXI.getStartingXICode(), startingXI.getPlayer1(), startingXI.getPlayer2(), startingXI.getPlayer3(), startingXI.getPlayer4(), startingXI.getPlayer5(), startingXI.getPlayer6(), startingXI.getPlayer7(), startingXI.getPlayer8(), startingXI.getPlayer9(), startingXI.getPlayer10(), startingXI.getPlayer11());
        return DatabaseConnection.writeQuery("startingXI", values);
    }

    public static void updateStartingXI(StartingXI startingXI){
        String values = String.format("player1='%s', player2='%s', player3='%s', player4='%s', player5='%s', player6='%s', player7='%s', player8='%s', player9='%s', player10='%s', player11='%s' WHERE startingXICode='%s'", startingXI.getPlayer1(), startingXI.getPlayer2(), startingXI.getPlayer3(), startingXI.getPlayer4(), startingXI.getPlayer5(), startingXI.getPlayer6(), startingXI.getPlayer7(), startingXI.getPlayer8(), startingXI.getPlayer9(), startingXI.getPlayer10(), startingXI.getPlayer11(), startingXI.getStartingXICode());
        updateQuery("startingXI", values);
    }

    public static int countStartingXI(){
        return countQuery("startingXI", "startingXICode");
    }

    public static void main(String[] args) {

        System.out.println(countStartingXI());
    }
}
