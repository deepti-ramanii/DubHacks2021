package database;

import java.util.Arrays;

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

    public boolean equals(UserInfo other) {
        return (this.age ==  other.age && Arrays.equals(this.playstyleInfo, other.playstyleInfo) && Arrays.equals(this.hobbyInfo, other.hobbyInfo));
    }
}
