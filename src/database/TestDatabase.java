package database;

import components.PlayerMatcher;

public class TestDatabase {
    public static void main(String[] args) {
        PlayerInfoDatabaseHelper dbHelper = PlayerInfoDatabaseHelper.getInstance();

        // test adding into database, normal case
        UserInfo aa_info = new UserInfo(15, new boolean[] {true, true}, new boolean[] {true});
        UserPreferences aa_prefs = new UserPreferences(13, 18, new boolean[] {true, true}, false);
        dbHelper.addUser("alice-adams", aa_info);
        dbHelper.updatePreferences("alice-adams", aa_prefs);
        UserInfo test_aa_info = dbHelper.getInfo("alice-adams");
        UserPreferences test_aa_prefs = dbHelper.getPreferences("alice-adams");

        System.out.println("testing aa user info: " + aa_info.equals(test_aa_info));
        System.out.println("testing aa user prefs: "  + aa_prefs.equals(test_aa_prefs));

        // test updating prefs
        aa_prefs.matchUsingHobbies  = true;
        aa_info.age = 18;
        dbHelper.updatePreferences("alice-adams", aa_prefs);
        dbHelper.updateInfo("alice-adams", aa_info);

        // test getting nonexistent info and prefs
        UserInfo nonExistent = dbHelper.getInfo("nonexistent_user");
        UserPreferences nonExistentPrefs = dbHelper.getPreferences("nonexistent-user");
        System.out.println("testing non-existent info: "  + nonExistent);
        System.out.println("testing non-existent prefs: " + nonExistentPrefs);

        // test adding duplicate user
        boolean isSuccessful = dbHelper.addUser("alice-adams", aa_info);
        System.out.println("tried to add duplicate user; result: " + isSuccessful);
    }

}
