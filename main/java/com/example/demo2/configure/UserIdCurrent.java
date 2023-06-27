package com.example.demo2.configure;

public class UserIdCurrent {
    private static int userId;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        UserIdCurrent.userId = userId;
    }
}
