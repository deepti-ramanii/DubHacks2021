package server.data;

import java.sql.*;

public class PlayerInfoDatabaseHelper {
    // server info
    public static final String SERVER_NAME = "localhost:3306";
    public static final String SERVER_USERNAME = "java";
    public static final String SERVER_PASSWORD = "Password01";

    // database info
    public static final String DATABASE_NAME = "player_info_db";
    public static final String TABLE_NAME = "player_info";

    // player info columns
    public static final String PLAYER_ID = "player_id";
    public static final String PLAYER_AGE = "player_age";
    public static final String[] PLAYER_PLAYSTYLE = { "player_competitive", "player_uses_vc" };
    public static final String[] PLAYER_HOBBIES = { "likes_anime" };

    // player pref columns
    public static final String MIN_AGE_PREF = "min_age_pref";                                       // minimum age preferred (default: 0)
    public static final String MAX_AGE_PREF = "max_age_pref";                                       // max age preferred (default: 255)
    public static final String[] PLAYSTYLE_PREFS = { "competitive_pref", "uses_vc_pref" };          // array of player playstyle preferences (default: false)
    public static final String MATCH_USING_HOBBIES = "match_using_hobbies";                         // matching type preferred (default: false)

    // player matching columns
    public static final String IS_MATCHED = "is_matched";

    // connection to server where database is located
    private static Connection connection = null;

    // singleton class
    private static PlayerInfoDatabaseHelper instance = null;

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

    // try connecting to the database using the format: jdbc:mysql://host_name:port/database_name
    public static void connect() {
        String URL = "jdbc:mysql://" + SERVER_NAME + "/" + DATABASE_NAME + "?characterEncoding=latin1";
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
            if(resultSet.next()) {
                return (resultSet.getInt(1) > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isMatched(String user_id) {
        if (!isInTable(user_id)) {
            return false;
        }
        String checkIsMatchedQuery = "SELECT " + IS_MATCHED +
                                     " FROM " + TABLE_NAME +
                                     " WHERE " + PLAYER_ID + " = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(checkIsMatchedQuery);
            statement.setString(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setMatched(String user_id, boolean is_matched) {
        if (!isInTable(user_id)) {
            return false;
        }
        String setMatchedQuery = "UPDATE " + TABLE_NAME +
                                 " SET " + IS_MATCHED + " = ?" +
                                 " WHERE " + PLAYER_ID  + " = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(setMatchedQuery);
            statement.setBoolean(1, is_matched);
            statement.setString(2, user_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e)  {
            e.printStackTrace();
            return false;
        }
    }

    // add user id to table  w/ associated preferences set to null
    // returns true if the user id was successfully added
    // returns false if user id already exists in the table
    public boolean addUser(String user_id, UserInfo user_info) {
        if (isInTable(user_id)) {
            return false;
        }
        String insertIDQuery = "INSERT INTO " + TABLE_NAME +
                               " (" + PLAYER_ID + "," +
                                      PLAYER_AGE + "," +
                                      PLAYER_PLAYSTYLE[0] + "," +
                                      PLAYER_PLAYSTYLE[1] + "," +
                                      PLAYER_HOBBIES[0] + ")" +
                               " VALUES (?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertIDQuery);
            statement.setString(1, user_id);
            statement.setInt(2, user_info.age);
            for (int i = 0; i < PLAYER_PLAYSTYLE.length; i++) {
                statement.setBoolean(3  + i, user_info.playstyleInfo[i]);
            }
            for (int i = 0; i < PLAYER_HOBBIES.length; i++) {
                statement.setBoolean(3 + PLAYER_PLAYSTYLE.length + i, user_info.hobbyInfo[i]);
            }
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
    public boolean updatePreferences(String user_id, UserPreferences user_prefs) {
        if (!isInTable(user_id)) {
            return false;
        }
        String updatePrefsQuery = "UPDATE " + TABLE_NAME +
                                  " SET " + MIN_AGE_PREF + " = ?, " +
                                            MAX_AGE_PREF + " = ?, " +
                                            PLAYSTYLE_PREFS[0] + " = ?, " +
                                            PLAYSTYLE_PREFS[1] + " = ?, " +
                                            MATCH_USING_HOBBIES + " = ?" +
                                  " WHERE " + PLAYER_ID  + " = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updatePrefsQuery);
            statement.setInt(1, user_prefs.minAge);
            statement.setInt(2, user_prefs.maxAge);
            for (int i = 0; i < PLAYSTYLE_PREFS.length; i++) {
                statement.setBoolean(3  + i, user_prefs.playstylePrefs[i]);
            }
            statement.setBoolean(3 + PLAYSTYLE_PREFS.length, user_prefs.matchUsingHobbies);
            statement.setString(4 + PLAYSTYLE_PREFS.length, user_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // return true if info associated with given user id was successfully updated
    // returns false if user id not in table
    public boolean updateInfo(String user_id, UserInfo user_info) {
        if (!isInTable(user_id)) {
            return false;
        }
        String updateInfoQuery = "UPDATE " + TABLE_NAME +
                " SET " + PLAYER_AGE + " = ?, " +
                          PLAYER_PLAYSTYLE[0] + " = ?, " +
                          PLAYER_PLAYSTYLE[1] + " = ?, " +
                          PLAYER_HOBBIES[0] + " = ?" +
                " WHERE " + PLAYER_ID  + " = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateInfoQuery);
            statement.setInt(1, user_info.age);
            for (int i = 0; i < PLAYER_PLAYSTYLE.length; i++) {
                statement.setBoolean(2  + i, user_info.playstyleInfo[i]);
            }
            for (int i = 0; i < PLAYER_HOBBIES.length; i++) {
                statement.setBoolean(2 + PLAYER_PLAYSTYLE.length + i, user_info.hobbyInfo[i]);
            }
            statement.setString(2 + PLAYER_PLAYSTYLE.length + PLAYER_HOBBIES.length, user_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // returns preferences associated with given user id
    // returns null if user id not in table
    public UserPreferences getPreferences(String user_id) {
        if (!isInTable(user_id)) {
            return null;
        }
        String selectPrefsQuery = "SELECT "  + MIN_AGE_PREF + "," +
                                               MAX_AGE_PREF + "," +
                                               PLAYSTYLE_PREFS[0] + "," +
                                               PLAYSTYLE_PREFS[1] + "," +
                                               MATCH_USING_HOBBIES +
                                  " FROM " + TABLE_NAME +
                                  " WHERE " + PLAYER_ID + " = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(selectPrefsQuery);
            statement.setString(1, user_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int minAge = resultSet.getInt(1);
                int maxAge = resultSet.getInt(2);
                boolean[] playstylePrefs = new boolean[PLAYSTYLE_PREFS.length];
                for (int i = 0; i < PLAYSTYLE_PREFS.length; i++) {
                    playstylePrefs[i] = resultSet.getBoolean(3 + i);
                }
                boolean matchUsingHobbies = resultSet.getBoolean(3 + PLAYSTYLE_PREFS.length);
                return new UserPreferences(minAge, maxAge, playstylePrefs, matchUsingHobbies);
            }
        } catch (SQLException e)  {
            e.printStackTrace();
        }
        return null;
    }

    // returns info associated with given user id
    // returns null if user id not in table
    public UserInfo getInfo(String user_id) {
        if (!isInTable(user_id)) {
            return null;
        }
        String selectInfoQuery = "SELECT "  + PLAYER_AGE + "," +
                                              PLAYER_PLAYSTYLE[0] + "," +
                                              PLAYER_PLAYSTYLE[1] + "," +
                                              PLAYER_HOBBIES[0] +
                " FROM " + TABLE_NAME +
                " WHERE " + PLAYER_ID + " = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(selectInfoQuery);
            statement.setString(1, user_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int age = resultSet.getInt(1);
                boolean[] playstyleInfo = new boolean[PLAYER_PLAYSTYLE.length];
                for (int i = 0; i < PLAYER_PLAYSTYLE.length; i++) {
                    playstyleInfo[i] = resultSet.getBoolean(2 + i);
                }
                boolean[] hobbyInfo = new boolean[PLAYER_HOBBIES.length];
                for (int i = 0; i < PLAYER_HOBBIES.length; i++) {
                    hobbyInfo[i] = resultSet.getBoolean(2 + PLAYER_PLAYSTYLE.length + i);
                }
                return new UserInfo(age, playstyleInfo, hobbyInfo);
            }
        } catch (SQLException e)  {
            e.printStackTrace();
        }
        return null;
    }
}