import java.sql.*;
import java.lang.*;
import java.util.ArrayList;

public class Database {
    static Connection conn;
    static Statement stmt = null;
    static ResultSet rs = null;
    public static void connect()throws SQLException{
        String url = "jdbc:sqlite:C:/Users/Georg/IdeaProjects/FootballLeague/Football/leagueTable.db";
        conn = DriverManager.getConnection(url);
        System.out.println("Connection to the db was successful!");
    }

    public static void close(){
        System.out.println("DB is being closed by close()");
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) { /* ignored */}
        }
    }

    public static Player readPlayer(String playerCode){
        Player player = new Player();
        try {
            connect();
            System.out.println("Creating statement - Read Player");
            Statement stmt = conn.createStatement();
            System.out.println("Player code: " + playerCode);
            String sql = "SELECT * FROM player WHERE playerCode='" + playerCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs.next());
//            if (rs.next()){
                String forename = rs.getString("forename");
                String surname = rs.getString("surname");
                String injuryStatusStr = rs.getString("injuryStatus");
                Boolean injuryStatus = (injuryStatusStr == "true");
                String teamCode = rs.getString("teamCode");
                player = new Player(playerCode, forename, surname, injuryStatus, teamCode);
//            }
        } catch (SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        System.out.println(player.toString());
        return player;
    }

    //Returns an ArrayList of players that belong to the team entered
    public static ArrayList<Player> readPlayersTeam(String teamCode){
        ArrayList<Player> players = new ArrayList<>();
        try {
            connect();
            System.out.println("Creating statement - Read Players Team");
            Statement stmt = conn.createStatement();
            String sql = "SELECT playerCode FROM player WHERE teamCode='" + teamCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String playerCode = rs.getString("playerCode");
                String forename = rs.getString("forename");
                String surname = rs.getString("surname");
                String injuryStatusStr = rs.getString("injuryStatus");
                Boolean injuryStatus = (injuryStatusStr.equals("true"));
                players.add(new Player(playerCode, forename, surname, injuryStatus, teamCode));
            }
        } catch (SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return players;
    }

    public static void writePlayer(Player player){
        try {
            connect();
            System.out.println("Creating statement - Write Player");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO player VALUES ('" + player.getPlayerCode() + "', '" + player.getForename() + "', '" + player.getSurname() + "', '" + player.getInjuryStatus() + "', '" + player.getTeamCode() + "');";
            stmt.executeUpdate(sql);
        }catch (SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    public static void updatePlayer(Player player){
        try {
            connect();
            System.out.println("Creating statement - Update Player");
            Statement stmt = conn.createStatement();
            String sql = "UPDATE player SET forename='" + player.getForename() + "', surname='" + player.getSurname() + "', injuryStatus='" + player.getInjuryStatus() + "',teamCode='" + player.getTeamCode() + "' WHERE playerCode='" + player.getPlayerCode() + "';";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch(SQLException ex){
            System.out.println(ex);
        }finally {
            close();
        }
    }

    public static void writeTeam(Team team){
        try {
            connect();
            System.out.println("Creating statement - Write Team");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO teams VALUES ('" + team.getTeamCode() + "', '" + team.getName() + "', '" + team.getVenue().getName() + "');";
            stmt.executeUpdate(sql);
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    //TODO readPlayer() inside Team constructor for readTeam
    public static Team readTeam(String teamCode){
        Team team = new Team();
        try {
            connect();
            System.out.println("Creating Statement");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM teams WHERE teamCode='" + teamCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String teamName = rs.getString("teamName");
                String players = rs.getString("players");
                String venue = rs.getString("Venue");
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        //TODO build team object
        return team;
    }

    public static int countPlayers(){
        int count = 0;
        try {
            connect();
            System.out.println("Creating Statement - Count Players");
            Statement stmt = conn.createStatement();
            String sql = "SELECT playerCode FROM player;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                count++;
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return count;
    }

    public static int checkDuplication(String playerCode){
        int count = 0;
        try {
            connect();
            System.out.println("Creating Statement - Count Players");
            Statement stmt = conn.createStatement();
            String sql = "SELECT playerCode FROM player WHERE playerCode='" + playerCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                count++;
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return count;
    }

    public static void main(String[] args){
        writePlayer(Player.genPlayer(1).get(0));
        writePlayer(Player.genPlayer(1).get(0));
        writeTeam(Team.genTeam(1).get(0));
    }
}
