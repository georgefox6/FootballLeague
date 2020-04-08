package FootballLeague.FootballLeagueBackend;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DatabaseConnection {

    public static Logger logger = LogManager.getLogger("com.josh");

    static String connectionUrl;
    static Connection connection;
    static Statement statement;
    static ResultSet results;

    public static void connect() throws SQLException {
        connectionUrl = "jdbc:sqlite:src/main/resources/SaveGames/" + GameState.readSaveName() + ".db";
        connection = DriverManager.getConnection(connectionUrl);
    }

    public static void connectMain() throws SQLException {
        connectionUrl = "jdbc:sqlite:src/main/resources/leagueTable.db";
        connection = DriverManager.getConnection(connectionUrl);
    }

    public static void close(){
        if (results != null) {
            try {
                results.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) { /* ignored */}
        }

    }

    public static ResultSet readQuery(String table, String condition) {
        try {
            connect();
            statement = connection.createStatement();
            String sql = "SELECT * FROM " + table + " WHERE " + condition + "';";
            results = statement.executeQuery(sql);
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeQuery(String table, String values){
        try{
            connect();
            statement = connection.createStatement();
            String sql = "INSERT INTO " + table + " VALUES (" + values + ");";
            statement.executeUpdate(sql);
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
    }

    public static void updateQuery(String table, String values){
        try {
            connect();
            statement = connection.createStatement();
            String sql = "UPDATE " + table + " SET " + values;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public static int countQuery(String table, String id){
        int counter = 0;
        try {
            connect();
            statement = connection.createStatement();
            String sql = "SELECT " + id + " FROM " + table + " ;";
            results = statement.executeQuery(sql);
            while(results.next()){
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return counter;
    }

    public static ResultSet readAllQuery(String table, String clause){
        try {
            connect();
            statement = connection.createStatement();
            String sql = "SELECT * FROM " + table + " " + clause + ";";
            results = statement.executeQuery(sql);
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet readAllFromMainQuery(String table, String clause){
        try {
            connectMain();
            statement = connection.createStatement();
            String sql = "SELECT * FROM " + table + " " + clause + ";";
            results = statement.executeQuery(sql);
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}