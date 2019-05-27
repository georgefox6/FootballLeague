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

    ///////////////////////////////////////////////////
    //              DATABASE MANAGEMENT              //
    ///////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////
    //               PLAYER MANAGEMENT               //
    ///////////////////////////////////////////////////

    //Method used to read the player from the DB and return a player object
    public static Player readPlayer(String playerCode){
        Player player = new Player();
        try {
            connect();
            System.out.println("Creating statement - Read Player");
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM player WHERE playerCode='" + playerCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
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
        return player;
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

    //Returns an ArrayList of players that belong to the team entered
    public static ArrayList<Player> readPlayersTeam(String teamCode){
        ArrayList<Player> players = new ArrayList<>();
        try {
            connect();
            System.out.println("Creating statement - Read Players Team");
            Statement stmt = conn.createStatement();
            String sql = "SELECT playerCode FROM player WHERE teamCode='" + teamCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(sql);
            while (rs.next()) {
                String playerCode = rs.getString("playerCode");
                players.add(readPlayer(playerCode));
            }
        } catch (SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return players;
    }

    ///////////////////////////////////////////////////
    //                TEAM MANAGEMENT                //
    ///////////////////////////////////////////////////

    public static Team readTeam(String teamCode){
        Team team = new Team();
        try {
            connect();
            System.out.println("Creating Statement - Read Team");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM teams WHERE teamCode='" + teamCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String teamName = rs.getString("teamName");
                String venueName = rs.getString("Venue");
                Venue venue = new Venue(venueName, 60000);
                team = new Team(teamCode, teamName, venue);
                //TODO add venue to team object
                team = new Team(teamCode, teamName, venue);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return team;
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

    public static void updateTeam(Team team){
        try{
            connect();
            System.out.println("Creating statement - Update Team");
            Statement stmt = conn.createStatement();
            String sql = "UPDATE teams SET teamName='" + team.getName() + "', Venue='" + team.getVenue().getName() + "' WHERE teamCode='" + team.getTeamCode() + "';";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
    }

    ///////////////////////////////////////////////////
    //               VENUE MANAGEMENT                //
    ///////////////////////////////////////////////////

    //TODO readVenue
    //TODO writeVenue
    //TODO updateVenue

    ///////////////////////////////////////////////////
    //                MISC MANAGEMENT                //
    ///////////////////////////////////////////////////

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

    public static int countTeams(){
        int count = 0;
        try {
            connect();
            System.out.println("Creating Statement - Count Teams");
            Statement stmt = conn.createStatement();
            String sql = "SELECT teamCode FROM teams;";
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
//        Venue ot = new Venue("Old Trafford", 65000);
//        Team team = new Team("Manchester United", ot);
//        writeTeam(team);
//        System.out.println(readTeam("000Man"));
 //       Player player = new Player("Kay", "Sutcliffe", false, "000Man");
 //       writePlayer(player);
 //       System.out.println(player);
 //       player.setSurname("Fox");
 //       updatePlayer(player);
 //       System.out.println(player.getSurname());

//        ArrayList<Player> players = readPlayersTeam("000Man");
//        for( int i = 0; i < players.size(); i++){
//            System.out.println(players.get(i).getForename());
//        }

//        System.out.println(readTeam("000Man").getName());

        Team ev = readTeam("007EVE");
        ev.setName("Newcastle");
        updateTeam(ev);

    }
}
