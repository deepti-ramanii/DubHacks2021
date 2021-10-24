package components;
import database.*;

import java.util.*;

public class PlayerMatcher {
    // has to be at least a 75% match, otherwise keep waiting
    public static final float MIN_MATCH_LEVEL = 0.75f;

    public static PlayerInfoDatabaseHelper playerInfoDB = PlayerInfoDatabaseHelper.getInstance();

    // store all players waiting for a match (queue -> first player is
    public Queue<String> waitingPlayers =  new LinkedList<String>();

    public void addToQueue(String id) {
        waitingPlayers.add(id);
    }

    // returns the id of the other player to match with if there's a good match,
    // returns null otherwise
    public String match(String currPlayerID) {
        // get match levels
        TreeMap<Float, String> matchLevels =  new TreeMap<Float, String>();
        for (String otherPlayerID : waitingPlayers) {
            UserInfo currInfo = playerInfoDB.getInfo(currPlayerID);
            UserPreferences currPrefs = playerInfoDB.getPreferences(currPlayerID);
            UserInfo otherInfo = playerInfoDB.getInfo(otherPlayerID);
            UserPreferences otherPrefs = playerInfoDB.getPreferences(otherPlayerID);

            float numMatchingPrefs = 0.0f;
            float numTotalPrefs  = 1 + currInfo.playstyleInfo.length + currInfo.hobbyInfo.length;

            // TODO: weighted the preferences better?
            // check age
            if (currInfo.age < otherPrefs.maxAge && currInfo.age > otherPrefs.minAge) {
                numMatchingPrefs += 0.5f;
            }
            if (otherInfo.age < currPrefs.maxAge && otherInfo.age > currPrefs.minAge) {
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
            for (int i = 0; i < currPrefs.playstylePrefs.length; i++) {
                if (currPrefs.matchUsingHobbies && currInfo.hobbyInfo[i] == otherInfo.hobbyInfo[i])  {
                    numMatchingPrefs += 1.0f;
                }
            }
            matchLevels.put((numMatchingPrefs / numTotalPrefs), otherPlayerID);
        }

        // check if there's a good match
        float highestMatch = waitingPlayers.size() > 0 ? matchLevels.firstKey(): 0.0f;
        if (highestMatch >= MIN_MATCH_LEVEL) {
            return matchLevels.get(highestMatch);
        }
        return null;
    }
}
