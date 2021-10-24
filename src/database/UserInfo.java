package database;

// store info about player
public class UserInfo {
    public int age;
    public boolean[] playstyleInfo; // [competitive, usesVC]
    public boolean[] hobbyInfo;  // list of hobbies

    public UserInfo(int age, boolean[] playstyleInfo, boolean[] hobbyInfo) {
        this.age = age;
        this.playstyleInfo = playstyleInfo;
        this.hobbyInfo = hobbyInfo;
    }
}
