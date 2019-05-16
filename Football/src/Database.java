import java.sql.*;
import java.lang.*;
public class Database {
    static Connection conn = null;
    public static void connect(){
        try{
            String url = "jdbc:sqlite:C:/Users/gfox/OneDrive - Sopra Steria/Apprenticeship/Training weeks/OOP/leagueTable.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to the db was successful!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } /*finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }*/
    }
    public static void writePlayer(Player player) throws SQLException{
        connect();
        System.out.println("Creating statement");
        Statement stmt = conn.createStatement();
        String sql = "INSERT INTO player VALUES ('" + player.getPlayerCode() + "', '" +  player.getForename() + "', '" + player.getSurname() + "', '" + player.getInjuryStatus() + "');";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void writeTeam(Team team) throws SQLException{
        connect();
        System.out.println("Creating statement");
        Statement stmt = conn.createStatement();
        String sql = "INSERT INTO teams VALUES ('" + team.getTeamCode() + "', '" +  team.getName() + "', '" + team.listPlayers() + "', '" + team.getVenue().getName() + "');";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public static void main(String[] args) throws SQLException{
        writePlayer(Player.genPlayer(1).get(0));
        writePlayer(Player.genPlayer(1).get(0));
        writeTeam(Team.genTeam(1).get(0));
    }
}
