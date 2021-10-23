package database;

public class Preferences {
    public int minAge;
    public int maxAge;

    public int skillLevel;

    public Preferences(int minAge, int maxAge, int skillLevel) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.skillLevel = skillLevel;
    }
}