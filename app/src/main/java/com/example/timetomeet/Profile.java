package com.example.timetomeet;

public class Profile {
    static String username;

    public static String getUsername() {
        return username;
    }

    static void setUsername(String username) {
        Profile.username = username;
    }
}
