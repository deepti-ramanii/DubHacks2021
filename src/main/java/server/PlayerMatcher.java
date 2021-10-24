package server;

import server.data.*;
import java.util.*;

public class PlayerMatcher {
    // TODO: has to be at least a 75% match, otherwise keep waiting
    // public static final float MIN_MATCH_LEVEL = 0.75f;
    public static PlayerMatcher instance;

    public static PlayerInfoDatabaseHelper playerInfoDB = PlayerInfoDatabaseHelper.getInstance();

    // store all players waiting for a match (queue -> first player is
    public Set<String> waitingPlayersUsingPrefs;

    public static synchronized PlayerMatcher getInstance() {
        if (instance == null) {
            instance = new PlayerMatcher();
        }
        return instance;
    }

    private PlayerMatcher() {
        this.waitingPlayersUsingPrefs = new HashSet<String>();
    }

    // returns the id of the other player to match with if there's a good match,
    // returns null otherwise
    public String matchUsingPrefs(String currPlayerID) {
        // get curr info + prefs
        UserInfo currInfo = playerInfoDB.getInfo(currPlayerID);
        UserPreferences currPrefs = playerInfoDB.getPreferences(currPlayerID);

        // get match levels
        TreeMap<Float, String> matchLevels =  new TreeMap<Float, String>(Collections.reverseOrder());

        // compare against other players waiting for matches
        for (String otherPlayerID : waitingPlayersUsingPrefs) {
            UserInfo otherInfo = playerInfoDB.getInfo(otherPlayerID);
            UserPreferences otherPrefs = playerInfoDB.getPreferences(otherPlayerID);

            float numMatchingPrefs = 0.0f;
            float numTotalPrefs  = 1 + currInfo.playstyleInfo.length + (currPrefs.matchUsingHobbies ? currInfo.hobbyInfo.length : 0);

            // check age
            if (currInfo.age > otherPrefs.maxAge) {
                numMatchingPrefs += (0.4f * otherPrefs.maxAge / currInfo.age);
            } else if (currInfo.age < otherPrefs.minAge) {
                numMatchingPrefs += (0.4f * currInfo.age / otherPrefs.minAge);
            } else {
                numMatchingPrefs += 0.5f;
            }
            if (otherInfo.age > currPrefs.maxAge) {
                numMatchingPrefs += (0.4f * currPrefs.maxAge / otherInfo.age);
            } else if (otherInfo.age < currPrefs.minAge) {
                numMatchingPrefs += (0.4f * otherInfo.age / currPrefs.minAge);
            } else {
                numMatchingPrefs += 0.5f;
            }

            // check playstyle matching
            for (int i = 0; i < currPrefs.playstylePrefs.length; i++) {
                if (currInfo.playstyleInfo[i] ==  otherPrefs.playstylePrefs[i]) {
                    numMatchingPrefs += 0.5f;
                }
                if (otherInfo.playstyleInfo[i] == currPrefs.playstylePrefs[i])  {
                    numMatchingPrefs += 0.5f;
                }
            }

            // check hobby matching
            for (int i = 0; i < currInfo.hobbyInfo.length; i++) {
                if (currPrefs.matchUsingHobbies && currInfo.hobbyInfo[i] == otherInfo.hobbyInfo[i])  {
                    numMatchingPrefs += 1.0f;
                }
            }

            // save match level
            matchLevels.put((numMatchingPrefs / numTotalPrefs), otherPlayerID);
        }

        // TODO: if a match above a certain level isn't found, add curr player to waiting players and wait
        //  -> when a player that matches them finally gets added, then return for both
        //  -> how to do this?????

        if (matchLevels.keySet().size() <= 0) {
            waitingPlayersUsingPrefs.add(currPlayerID);
            playerInfoDB.setMatched(currPlayerID, false);
            return null;
        }
        String otherPlayerID = matchLevels.firstEntry().getValue();
        waitingPlayersUsingPrefs.remove(otherPlayerID);
        playerInfoDB.setMatched(currPlayerID, true);
        playerInfoDB.setMatched(otherPlayerID, true);
        return otherPlayerID;
    }
}
