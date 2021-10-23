package database;

import java.sql.*;

public class PlayerInfoDatabaseHelper {
    // server info
    public static final String SERVER_NAME = "localhost:3306";
    public static final String SERVER_USERNAME = "java";
    public static final String SERVER_PASSWORD = "Password01";

    // database info
    public static final String DATABASE_NAME = "player_info.db";
    public static final String TABLE_NAME = "player_info";
    public static final String PLAYER_ID = "player_id";

    public static final String MIN_AGE_PREF = "min_age";
    public static final String MAX_AGE_PREF = "max_age";
    public static final String SKILL_LEVEL_PREF = "skill_level";

    // connection to server where database is located
    private static Connection connection = null;

    // singleton class
    private static PlayerInfoDatabaseHelper instance = null;

    // try connecting to the database using the format: jdbc:mysql://hostname:port/databasename
    public static void connect() {
        String URL = "jdbc:mysql://" + SERVER_NAME + "/" + DATABASE_NAME;
        try {
            connection = DriverManager.getConnection(URL, SERVER_USERNAME, SERVER_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // close connection
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        }  catch (SQLException e)  {
            e.printStackTrace();
        }
    }

    // get instance of PlayerInfoDatabaseHelper (or create one if null)
    public static synchronized PlayerInfoDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new PlayerInfoDatabaseHelper();
        }
        return instance;
    }

    // create new instance of PlayerInfoDatabaseHelper and connect to the database
    private PlayerInfoDatabaseHelper() {
        connect();
    }

    // returns true if given user id is in table
    // returns false otherwise
    public boolean isInTable(String user_id) {
        String checkIDExistsQuery = "SELECT COUNT(*)" +
                                    " FROM " + TABLE_NAME +
                                    " WHERE " + PLAYER_ID + " = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(checkIDExistsQuery);
            statement.setString(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.getInt(1) > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // add user id to table  w/ associated preferences set to null
    // returns true if the user id was successfully added
    // returns false if user id already exists in the table
    public boolean addUser(String user_id) {
        if (isInTable(user_id)) {
            return false;
        }
        String insertIDQuery = "INSERT INTO " + TABLE_NAME +
                               " (" + PLAYER_ID + ")" +
                               " VALUES (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertIDQuery);
            statement.setString(1, user_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e)  {
            e.printStackTrace();
            return false;
        }
    }

    // update preferences
    // return true if preferences associated with given user id were successfully updated
    // returns false if user id not in table
    public boolean updatePreferences(String user_id, Preferences preferences) {
        if (!isInTable(user_id)) {
            return false;
        }
        String updatePrefsQuery = "UPDATE " + TABLE_NAME +
                                  " SET " + MIN_AGE_PREF + " = ?, " +
                                            MAX_AGE_PREF + " = ?, " +
                                            SKILL_LEVEL_PREF + " = ?" +
                                  " WHERE " + PLAYER_ID  + " = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updatePrefsQuery);
            statement.setInt(1, preferences.minAge);
            statement.setInt(2, preferences.maxAge);
            statement.setInt(3, preferences.skillLevel);
            statement.setString(4, user_id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // returns preferences associated with given user id
    // returns null if user id not in table
    public Preferences getPreferences(String user_id) {
        if (!isInTable(user_id)) {
            return null;
        }
        String selectPrefsQuery = "SELECT "  + MIN_AGE_PREF + "," + MAX_AGE_PREF + "," + SKILL_LEVEL_PREF +
                                  " FROM " + TABLE_NAME +
                                  " WHERE " + PLAYER_ID + " = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(selectPrefsQuery);
            statement.setString(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            return new Preferences(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3));
        } catch (SQLException e)  {
            e.printStackTrace();
            return null;
        }
    }
}