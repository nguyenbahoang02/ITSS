package com.example.demo2.configure;

public class UserIdTable {
    private static int userId;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        UserIdTable.userId = userId;
    }
}
