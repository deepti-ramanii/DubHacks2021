import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class PlayerInfoDatabaseHelper {
    // server info
    public static final String SERVER_NAME = "localhost:3306"
    public static final String SERVER_USERNAME = "java";
    public static final String SERVER_PASSWORD = "Password01";

    // database info
    public static final String DATABASE_NAME = "player_info.db";
    public static final String TABLE_NAME = "player_info";
    public static final String PLAYER_ID = "player_id";

    public static final String[] PLAYER_PREFS = ["min_age", "max_age", "skill_level"];

    // connection to server where database is located
    Connection connection = null;

    // singleton class
    private static PlayerInfoDatabaseHelper instance = null;

    // try connecting to the database using the format: jdbc:mysql://hostname:port/databasename
    public static void connect() {
        String URL = "jdbc:mysql://" + SERVER_NAME + "/" + DATABASE_NAME;
        try (connection = DriverManager.getConnection(URL, SERVER_USERNAME, SERVER_PASSWORD)){
            System.out.println("Successfully connected to database.")
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // close connection
    public static void closeConnection() {
        if (connection != null) {
            connection.close();
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
    public bool isInTable(String user_id) {
        String checkIDExistsQuery = "SELECT COUNT(*)" +
                                    " FROM " + TABLE_NAME +
                                    " WHERE " + PLAYER_ID + " = ?";
        PreparedStatement statement = connection.prepareStatement(checkIDExistsQuery);
        statement.setString(1, user_id);
        ResultSet resultSet = statement.execute();
        return (resultSet.getInt(1) > 0);
    }

    // add user id to table  w/ associated preferences set to null
    // returns true if the user id was successfully added
    // returns false if user id already exists in the table
    public bool addUser(String user_id) {
        if (isInTable) {
            return false;
        }
        String insertIDQuery = "INSERT INTO " + TABLE_NAME +
                               " (" + PLAYER_ID + ")" +
                               " VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(insertIDQuery);
        statement.SetString(1, user_id);
        statement.executeUpdate();
        return true;
    }

    // update preferences
    // return true if preferences associated with given user id were successfully updated
    // returns false if user id not in table
    public void updatePreferences(String user_id, Preferences preferences) {
        if (!isInTable) {
            return false;
        }
        String updatePrefsQuery = "UPDATE " + TABLE_NAME +
                                  " SET " + PLAYER_PREFS[0] + " = ?, " +
                                            PLAYER_PREFS[1] + " = ?, " +
                                            PLAYER_PREFS[3] + " = ?" +
                                  " WHERE " + PLAYER_ID  + " = ?";
        PreparedStatement statement = connection.prepareStatement(updatePrefsQuery);
        statemenet.setInt(1, preferences.minAge);
        statement.setInt(2, preferences.maxAge);
        statement.setInt(3, preferences.skillLevel);
        statement.setInt(4, user_id);
        return true;
    }

    // returns preferences associated with given user id
    // returns null if user id not in table
    public void getPreferences(String user_id) {
        if (!isInTable) {
            return null;
        }
        String selectPrefsQuery = "SELECT "  + PLAYER_PREFS[0] + "," + PLAYER_PREFS[1] + "," + PLAYER_PREFS[2] +
                                  " FROM " + TABLE_NAME +
                                  " WHERE " + PLAYER_ID + " = ?";
        PreparedStatement statement = connection.prepareStatement(selectPrefsQuery);
        statement.setString(1, user_id);
        ResultSet resultSet = statement.executeQuery();
        return new Preferences(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3));
    }
}