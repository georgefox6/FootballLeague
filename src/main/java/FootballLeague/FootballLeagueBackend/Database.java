package FootballLeagueBackend;
import java.sql.*;
import java.lang.*;
import java.util.ArrayList;

@Deprecated
public class Database {
    static Connection conn;
    static Statement stmt = null;
    static ResultSet rs = null;
    public static void connect()throws SQLException{
        String url = "jdbc:sqlite:Football/leagueTable.db";
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
            //if (rs.next()){
                String forename = rs.getString("forename");
                String surname = rs.getString("surname");
                String injuryStatusStr = rs.getString("injuryStatus");
                Boolean injuryStatus = (injuryStatusStr == "true");
                String teamCode = rs.getString("teamCode");
                player = new Player(playerCode, forename, surname, injuryStatus, teamCode);
                //}
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

    //Returns all players
    public static ArrayList<Player> readAllPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        try {
            connect();
            System.out.println("Creating statement - Read All Players");
            Statement stmt = conn.createStatement();
            String sql = "SELECT playerCode FROM player;";
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
            String sql = " SELECT * FROM team WHERE teamCode='" + teamCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String teamName = rs.getString("teamName");
                String league = rs.getString("league");
                String clubCode = rs.getString("club");
                team = new Team(teamCode, teamName, league, clubCode);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return team;
    }

    public static ArrayList<String> readTeamsFromLeague(String league) {
        ArrayList<String> teamList = new ArrayList<String>();
        try {
            connect();
            System.out.println("Creating Statement - Read Teams From League");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM team WHERE league='" + league + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String teamCode = rs.getString("teamCode");
                teamList.add(teamCode);
            }
        } catch(SQLException ex) {
            System.out.println(ex);
        } finally {
            close();
        }
        return teamList;
    }

    public static void writeTeam(Team team){
        try {
            connect();
            System.out.println("Creating statement - Write Team");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO team VALUES ('" + team.getTeamCode() + "', '" + team.getName() + "', '" + team.getLeague() + "', '" + team.getClubCode() + "');";
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
            String sql = "UPDATE team SET teamName='" + team.getName() + "', league='" + team.getLeague() + "', club='" + team.getClubCode() + "' WHERE teamCode='" + team.getTeamCode() + "';";
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

    public static Venue readVenue(String venueCode){
        Venue venue = new Venue();
        try {
            connect();
            System.out.println("Creating Statement - Read Venue");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM venue WHERE venueCode='" + venueCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String venueName = rs.getString("venueName");
                int capacity = rs.getInt("capacity");
                int ticketPrice = rs.getInt("ticketPrice");
                venue = new Venue(venueCode, venueName, capacity, ticketPrice);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return venue;
    }

    public static void writeVenue(Venue venue){
        try {
            connect();
            System.out.println("Creating statement - Write Venue");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO venue VALUES ('" + venue.getVenueCode() + "', '" + venue.getName() + "', '" + venue.getCapacity() + "', '" + venue.getTicketPrice() + "');";
            stmt.executeUpdate(sql);
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    public static void updateVenue(Venue venue){
        try{
            connect();
            System.out.println("Creating statement - Update Venue");
            Statement stmt = conn.createStatement();
            String sql = "UPDATE venue SET venueName='" + venue.getName() + "', capacity='" + venue.getCapacity() + "', ticketPrice='" + venue.getTicketPrice() + "' WHERE venueCode='" + venue.getVenueCode() + "';";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
    }

    ///////////////////////////////////////////////////
    //               TACTIC MANAGEMENT               //
    ///////////////////////////////////////////////////


    //TODO test the read/write/update tactic functions

    public static Tactic readTactic(String tacticCode){
        Tactic tactic = new Tactic();
        try {
            connect();
            System.out.println("Creating Statement - Read Tactic");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM tactic WHERE tacticCode='" + tacticCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String startingXICode = rs.getString("startingXI");
                // Attack and defence score are INT in db so restrict these to [0, 100] in db
                // since attackScore and defence score are [0, 1] we divide the db value by 100
                // to return a double [0, 1].
                double attackScore = rs.getInt("attackScore") / 1;
                double defenceScore = rs.getInt("defenceScore") / 1;
                String formation = rs.getString("formation");
                String playStyle = rs.getString("playStyle");
                tactic = new Tactic(startingXICode, attackScore, defenceScore, formation, playStyle);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return tactic;
    }

    public static void writeTactic(Tactic tactic){
        try {
            connect();
            System.out.println("Creating statement - Write Tactic");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO tactic VALUES ('" + tactic.getTacticCode() + "', '" + tactic.getStartingXICode() +
                    "', '" + tactic.getAttackScore() + "', '" + tactic.getDefenceScore() +
                    "', '" + tactic.getFormation() + "', '" + tactic.getPlayStyle() + "');";
            stmt.executeUpdate(sql);
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    public static void updateTactic(Tactic tactic){
        try{
            connect();
            System.out.println("Creating statement - Update Tactic");
            Statement stmt = conn.createStatement();
            String sql = "UPDATE tactic SET startingXI='" + tactic.getStartingXICode()  + "', attackScore='" + tactic.getAttackScore() + "', defenseScore='" +
                    tactic.getDefenceScore() + "', formation='" + tactic.getFormation() + "', playStyle='" + tactic.getPlayStyle()
                    + "' WHERE tacticCode='" + tactic.getTacticCode() + "';";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
    }
    ///////////////////////////////////////////////////
    //                CLUB MANAGEMENT                //
    ///////////////////////////////////////////////////

    public static Club readClub(String clubCode){
        Club club = new Club();
        try {
            connect();
            System.out.println("Creating Statement - Read Club");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM club WHERE clubCode='" + clubCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String clubName = rs.getString("clubName");
                String venueCode = rs.getString("venue");
                club = new Club(clubCode, clubName, venueCode);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return club;
    }

    public static void writeClub(Club club){
        try {
            connect();
            System.out.println("Creating statement - Write Club");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO club VALUES ('" + club.getClubCode() + "', '" + club.getName() + "', '" + club.getVenue() + "');";
            stmt.executeUpdate(sql);
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    public static void updateClub(Club club){
        try{
            connect();
            System.out.println("Creating statement - Update Club");
            Statement stmt = conn.createStatement();
            String sql = "UPDATE club SET clubName='" + club.getName() + "', venue='" + club.getVenue() + "' WHERE clubCode='" + club.getClubCode() + "';";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
    }
    ///////////////////////////////////////////////////
    //               MATCH MANAGEMENT                //
    ///////////////////////////////////////////////////

    //TODO test the read/write/update match functions

    public static Match readMatch(String matchCode){
        Match match = new Match();
        try {
            connect();
            System.out.println("Creating Statement - Read Match");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM matches WHERE matchCode='" + matchCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String homeTeamCode = rs.getString("homeTeamCode");
                String awayTeamCode = rs.getString("awayTeamCode");
                String homeTacticCode = rs.getString("homeTacticCode");
                String awayTacticCode = rs.getString("awayTacticCode");
                String score = rs.getString("score");
                String date = rs.getString("date");
                match = new Match(matchCode, homeTeamCode, awayTeamCode, homeTacticCode, awayTacticCode, score, date);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return match;
    }

    public static void writeMatch(Match match){
        try {
            connect();
            System.out.println("Creating statement - Write Match");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO matches VALUES ('" + match.getMatchCode() + "', '" + match.getHomeTeamCode() + "', '" + match.getAwayTeamCode() + "', '" + match.getHomeTacticCode() + "', '" + match.getAwayTacticCode() + "', '" + match.getScore() + "', '" + match.getDate() + "');";
            stmt.executeUpdate(sql);
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    public static void updateMatch(Match match){
        try{
            connect();
            System.out.println("Creating statement - Update Match");
            Statement stmt = conn.createStatement();
            String sql = "UPDATE matches SET homeTeamCode='" + match.getHomeTeamCode() + "', awayTeamCode='" + match.getAwayTeamCode() + "', homeTacticCode='" + match.getHomeTacticCode() + "', awayTacticCode='" + match.getAwayTacticCode() + "', score='" + match.getScore() + "', date='" + match.getDate() + "' WHERE matchCode='" + match.getMatchCode() + "';";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
    }

    ///////////////////////////////////////////////////
    //            STARTING XI MANAGEMENT             //
    ///////////////////////////////////////////////////

    /*

    public static StartingXI readStartingXI(String startingXICode){
        StartingXI startingXI = new StartingXI();
        try {
            connect();
            System.out.println("Creating Statement - Read StartingXI");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM startingXI WHERE startingXICode='" + startingXICode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String player1 = rs.getString(1);
                String player2 = rs.getString(2);
                String player3 = rs.getString(3);
                String player4 = rs.getString(4);
                String player5 = rs.getString(5);
                String player6 = rs.getString(6);
                String player7 = rs.getString(7);
                String player8 = rs.getString(8);
                String player9 = rs.getString(9);
                String player10 = rs.getString(10);
                String player11 = rs.getString(11);
                String sub1 = rs.getString(12);
                String sub2 = rs.getString(13);
                String sub3 = rs.getString(14);
                String sub4 = rs.getString(15);
                String sub5 = rs.getString(16);
                String sub6 = rs.getString(17);
                String sub7 = rs.getString(18);
                startingXI = new StartingXI(startingXICode,player1,player2,player3,player4,player5,player6,player7,player8,player9,player10,player11);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return startingXI;
    }

    public static void writestartingXI(StartingXI startingXI){
        try {
            connect();
            System.out.println("Creating statement - Write Starting XI");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO startingXI VALUES ('" + startingXI.startingXICode + "', '" + startingXI.player1 + "', '" + startingXI.player2 + "', '" + startingXI.player3 + "', '" + startingXI.player4 + "', '" + startingXI.player5 + "', '" + startingXI.player6 + "', '" + startingXI.player7 + "', '" + startingXI.player8 + "', '" + startingXI.player9 + "', '" + startingXI.player10 + "', '" + startingXI.player11 + "', '" + startingXI.sub1 + "', '" + startingXI.sub2 + "', '" + startingXI.sub3 + "', '" + startingXI.sub4 + "', '" + startingXI.sub5 + "', '" + startingXI.sub6 + "', '" + startingXI.sub7 + "');";
            stmt.executeUpdate(sql);
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    public static void updateStartingXI(StartingXI startingXI){
        try{
            connect();
            System.out.println("Creating statement - Update StartingXI");
            Statement stmt = conn.createStatement();
            String sql = "UPDATE startingXI SET player1='" + startingXI.player1 + "', player2='" + startingXI.player2 + "', player3='" + startingXI.player3 + "', player4='" + startingXI.player4 + "', player5='" + startingXI.player5 + "', player6='" + startingXI.player6 + "', player7='" + startingXI.player7 + "', player8='" + startingXI.player8 + "', player9='" + startingXI.player9 + "', player10='" + startingXI.player10 + "', player11='" + startingXI.player11 + "', sub1='" + startingXI.sub1 + "', sub2='" + startingXI.sub2 + "', sub3='" + startingXI.sub3 + "', sub4='" + startingXI.sub4 + "', sub5='" + startingXI.sub5 + "', sub6='" + startingXI.sub6 + "', sub7='" + startingXI.sub7 + "' WHERE startingXICode='" + startingXI.startingXICode + "';";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
    }

     */

    ///////////////////////////////////////////////////
    //               LEAGUE POSITION                 //
    ///////////////////////////////////////////////////

    public static LeaguePosition readLeaguePosition(String leaguePositionCode){
        LeaguePosition leaguePosition = new LeaguePosition();
        try {
            connect();
            System.out.println("Creating Statement - Read LeaguePosition");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM leaguePosition WHERE leaguePositionCode='" + leaguePositionCode + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String league = rs.getString("league");
                int position = rs.getInt("position");
                String teamCode = rs.getString("teamCode");
                String teamName = rs.getString("teamName");
                int played = rs.getInt("played");
                int won = rs.getInt("won");
                int drawn = rs.getInt("drawn");
                int lost = rs.getInt("lost");
                int goalsScored = rs.getInt("goalsScored");
                int goalsConceded = rs.getInt("goalsConceded");
                int goalDifference = rs.getInt("goalDifference");
                int points = rs.getInt("points");
                leaguePosition = new LeaguePosition(leaguePositionCode,league,position,teamCode,teamName,played,won,drawn,lost,goalsScored,goalsConceded,goalDifference,points);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return leaguePosition;
    }

    public static void writeLeaguePosition(LeaguePosition leaguePosition){
        try {
            connect();
            System.out.println("Creating statement - Write leaguePosition");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO leaguePosition VALUES ('" + leaguePosition.getLeaguePositionCode() + "', '" + leaguePosition.getLeague() + "', '" + leaguePosition.getPosition() + "', '" + leaguePosition.getTeamCode() + "', '" + leaguePosition.getTeamName() + "', '" + leaguePosition.getPlayed() + "', '" + leaguePosition.getWon() +  "', '" + leaguePosition.getDrawn() + "', '" + leaguePosition.getLost() + "', '" + leaguePosition.getGoalsScored() + "', '" + leaguePosition.getGoalsConceded() + "', '" + leaguePosition.getGoalDifference() + "', '" + leaguePosition.getPoints() + "');";
            stmt.executeUpdate(sql);
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    public static void updateleaguePosition(LeaguePosition leaguePosition){
        try{
            connect();
            System.out.println("Creating statement - Update leaguePosition");
            Statement stmt = conn.createStatement();
            String sql = "UPDATE leaguePosition SET leaguePositionCode='" + leaguePosition.getLeaguePositionCode() + "', league='" + leaguePosition.getLeague() + "', position='" + leaguePosition.getPosition() + "', teamCode='" + leaguePosition.getTeamCode() + "', teamName='" + leaguePosition.getTeamName() + "', played='" + leaguePosition.getPlayed() + "', won='" + leaguePosition.getWon() + "', drawn='" + leaguePosition.getDrawn() + "', lost='" + leaguePosition.getLost() + "', goalsScored='" + leaguePosition.getGoalsScored() + "', goalsConceded='" + leaguePosition.getGoalsConceded() + "', goalDifference='" + leaguePosition.getGoalDifference() + "', points='" + leaguePosition.getPoints() + "' WHERE leaguePositionCode='" + leaguePosition.getLeaguePositionCode() + "';";
            stmt.executeUpdate(sql);
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
    }

    public static ArrayList<LeaguePosition> readLeaguePositionLeague(String league){
        ArrayList<LeaguePosition> positionsArray = new ArrayList<>();
        try {
            connect();
            System.out.println("Creating Statement - Read LeaguePosition");
            Statement stmt = conn.createStatement();
            String sql = " SELECT * FROM leaguePosition WHERE league='" + league + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String leaguePositionCode = rs.getString("leaguePositionCode");
                int position = rs.getInt("position");
                String teamCode = rs.getString("teamCode");
                String teamName = rs.getString("teamName");
                int played = rs.getInt("played");
                int won = rs.getInt("won");
                int drawn = rs.getInt("drawn");
                int lost = rs.getInt("lost");
                int goalsScored = rs.getInt("goalsScored");
                int goalsConceded = rs.getInt("goalsConceded");
                int goalDifference = rs.getInt("goalDifference");
                int points = rs.getInt("points");
                positionsArray.add(new LeaguePosition(leaguePositionCode,league,position,teamCode,teamName,played,won,drawn,lost,goalsScored,goalsConceded,goalDifference,points));
            }
        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
        return positionsArray;
    }



    ///////////////////////////////////////////////////
    //                 LEAGUE TABLE                  //
    ///////////////////////////////////////////////////

    //TODO Test updateLeagueTableHome and updateLeagueTableAway
    //Function used to update the league table of the home team given a match as input
    public static void updateLeagueTableHome(Match match){
        Team homeTeam = readTeam(match.getHomeTeamCode());
        Team awayTeam = readTeam(match.getAwayTeamCode());
        String[] scoreLine = match.getScore().split("-");
        int homeGoals = Integer.parseInt(scoreLine[0]);
        int awayGoals = Integer.parseInt(scoreLine[1]);
        try{
            connect();
            System.out.println("Creating statement - Update League Table Home");
            Statement stmt = conn.createStatement();
            //First we need to read the values from the database before updating them
            String sql = "SELECT * FROM " + homeTeam.getLeague() + " WHERE teamCode = '" + match.getHomeTeamCode() + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                //Values read from the league table
                int position = rs.getInt(0);
                int played = rs.getInt(3);
                int won = rs.getInt(4);
                int drawn = rs.getInt(5);
                int lost = rs.getInt(6);
                int goalsScored = rs.getInt(7);
                int goalsConceded = rs.getInt(8);
                int goalDifference = rs.getInt(9);
                int points = rs.getInt(10);

                //modifications after the game
                played++;
                if(homeGoals>awayGoals){
                    won++;
                    points = points + 3;
                }
                else if(homeGoals == awayGoals){
                    drawn++;
                    points = points + 1;
                }
                else if(homeGoals<awayGoals){
                    lost++;
                }
                goalsScored = goalsScored + homeGoals;
                goalsConceded = goalsConceded + awayGoals;
                goalDifference = goalsScored - goalsConceded;
                stmt = conn.createStatement();
                sql = "UPDATE " + homeTeam.getLeague() + " SET played='" + played + "' won='" + won + "' drawn='" + drawn + "' lost='" + lost + "' goalsScored='" + goalsScored + "' goalsConceded='" + goalsConceded + "' goalDifference='" + goalDifference + "' points='" + points + "' WHERE teamCode ='" + match.getHomeTeamCode() + "';";
                stmt.executeUpdate(sql);
            }

        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    //Function used to update the league table for the away team given a match as input
    public static void updateLeagueTableAway(Match match){
        Team homeTeam = readTeam(match.getHomeTeamCode());
        Team awayTeam = readTeam(match.getAwayTeamCode());
        String[] scoreLine = match.getScore().split("-");
        int homeGoals = Integer.parseInt(scoreLine[0]);
        int awayGoals = Integer.parseInt(scoreLine[1]);
        try{
            connect();
            System.out.println("Creating statement - Update League Table");
            Statement stmt = conn.createStatement();
            //First we need to read the values from the database before updating them
            String sql = "SELECT * FROM " + awayTeam.getLeague() + " WHERE teamCode = '" + match.getAwayTeamCode() + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                //Values read from the league table
                int position = rs.getInt(0);
                int played = rs.getInt(3);
                int won = rs.getInt(4);
                int drawn = rs.getInt(5);
                int lost = rs.getInt(6);
                int goalsScored = rs.getInt(7);
                int goalsConceded = rs.getInt(8);
                int goalDifference = rs.getInt(9);
                int points = rs.getInt(10);

                //modifications after the game
                played++;
                if(homeGoals<awayGoals){
                    won++;
                    points = points + 3;
                }
                else if(homeGoals == awayGoals){
                    drawn++;
                    points = points + 1;
                }
                else if(homeGoals>awayGoals){
                    lost++;
                }
                goalsScored = goalsScored + awayGoals;
                goalsConceded = goalsConceded + homeGoals;
                goalDifference = goalsScored - goalsConceded;
                stmt = conn.createStatement();
                sql = "UPDATE " + awayTeam.getLeague() + " SET played='" + played + "' won='" + won + "' drawn='" + drawn + "' lost='" + lost + "' goalsScored='" + goalsScored + "' goalsConceded='" + goalsConceded + "' goalDifference='" + goalDifference + "' points='" + points + "' WHERE teamCode ='" + match.getAwayTeamCode() + "';";
                stmt.executeUpdate(sql);
            }

        } catch(SQLException ex){
            System.out.println(ex);
        } finally {
            close();
        }
    }

    //TODO Create a function which updates the position of the team depending on their points then goal difference

    ///////////////////////////////////////////////////
    //              SCHEDULE MANAGEMENT              //
    ///////////////////////////////////////////////////

    // Method to write all matches produced as schedule to the database
    /// Open a database connection and iterate over list of matches for 
    /// each element produce a new SQL statement to execute
    public static void updateSchedule(ArrayList<Match> matches) {
        try {
            connect();
            int numberOfMatches = matches.size();
            for (int matchNumber=0; matchNumber<numberOfMatches; matchNumber++){
                System.out.println("Creating statement - Write Match");
                Statement stmt = conn.createStatement();
                // TODO add homeTeamTactic and awayTeamTactic
                Match match = matches.get(matchNumber);
                String sql = "INSERT INTO matches VALUES ('" + match.getMatchCode() + "' , '" + match.getHomeTeamCode() + "' , '" + match.getAwayTeamCode() + "' , '" + "homeTacticCode" + "' , '" + "awayTacticCode" + "' , '" + "score" + "' , '" + match.getDate() + "');";
                stmt.executeUpdate(sql);
            }    
            } catch (SQLException ex) {
                System.out.println(ex);
            } finally {
                close();
            }
    }

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
            System.out.println("Creating Statement - Count Team");
            Statement stmt = conn.createStatement();
            String sql = "SELECT teamCode FROM team;";
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

    public static int countTactics() {
        int count = 0;
        try {
            connect();
            System.out.println("Creating statement - Count Tactics");
            Statement stmt = conn.createStatement();
            String sql = "SELECT tacticCode FROM tactic;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                count++;
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return count;
    }

    public static int countVenues() {
        int count = 0;
        try {
            connect();
            System.out.println("Creating statement - Count Venues");
            Statement stmt = conn.createStatement();
            String sql = "SELECT venueCode FROM venue;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                count++;
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return count;
    }

    public static int countClubs() {
        int count = 0;
        try {
            connect();
            System.out.println("Creating statement - Count Clubs");
            Statement stmt = conn.createStatement();
            String sql = "SELECT clubCode FROM club;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                count++;
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }finally{
            close();
        }
        return count;
    }

    public static int countStartingXI(){
        int count =0;
        try {
            connect();
            System.out.println("Creating statement - Count StartingXI");
            Statement stmt = conn.createStatement();
            String sql = "SELECT startingXICode FROM startingXI";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                count++;
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }finally {
            close();
        }
        return count;
    }

    public static int countMatches() {
        int count =0;
        try {
            connect();
            System.out.println("Creating statement - Count Matches");
            Statement stmt = conn.createStatement();
                    String sql = "SELECT matchCode FROM matches";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                count++;
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }finally {
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
    }
}
