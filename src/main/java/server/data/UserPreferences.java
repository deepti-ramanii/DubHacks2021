package server.data;

import java.util.Arrays;

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

    public boolean equals(UserPreferences other) {
        return (this.minAge == other.minAge && this.maxAge == other.maxAge && Arrays.equals(this.playstylePrefs, other.playstylePrefs) && this.matchUsingHobbies == other.matchUsingHobbies);
    }
}