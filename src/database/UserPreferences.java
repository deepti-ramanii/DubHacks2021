package database;

// store info about player preferences
public class UserPreferences {
    public int minAge;
    public int maxAge;
    public boolean[] playstylePrefs; // [competitive, usesVC]
    public boolean matchUsingHobbies;

    public UserPreferences(int minAge, int maxAge, boolean[] playstylePrefs, boolean matchUsingHobbies) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.playstylePrefs = playstylePrefs;
        this.matchUsingHobbies = matchUsingHobbies;
    }
}