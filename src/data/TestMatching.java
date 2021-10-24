package database;

import components.PlayerMatcher;

public class TestMatching {
    public static void main(String[] args) {
        PlayerInfoDatabaseHelper dbHelper = PlayerInfoDatabaseHelper.getInstance();
        PlayerMatcher matcher = new PlayerMatcher();

        String matchEmpty = matcher.matchUsingPrefs("AshleyL-02");
        System.out.println("tried matching with empty queue; return: "  + matchEmpty);
        System.out.println(matcher.waitingPlayersUsingPrefs.toString());

        String matchOne = matcher.matchUsingPrefs("deeptii-ramani");
        System.out.println("tried matching with one person in queue; return: " + matchOne);
        System.out.println(matcher.waitingPlayersUsingPrefs.toString());

        matcher.waitingPlayersUsingPrefs.add("simranmalhi");
        matcher.waitingPlayersUsingPrefs.add("deeptii-ramani");
        matcher.waitingPlayersUsingPrefs.add("AshleyL-02");
        System.out.println(matcher.waitingPlayersUsingPrefs.toString());

        String matchThree = matcher.matchUsingPrefs("alice-adams");
        System.out.println("tried matching with three people in queue; return: " + matchThree);
        System.out.println(matcher.waitingPlayersUsingPrefs.toString());
    }

}
