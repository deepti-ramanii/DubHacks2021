package database;

// store info about player preferences
public class UserPreferences {
    public int minAge;
    public int maxAge;

    public int difficulty;

    public UserPreferences(int minAge, int maxAge, int difficulty) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.difficulty = difficulty;
    }
}