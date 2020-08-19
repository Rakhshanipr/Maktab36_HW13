package com.example.hw13.model;

import java.util.UUID;

public class User {
    private UUID mUserId;
    private String mUserName;
    private String mPassword;

    public static User sOnlineUser;

    public User(String userName, String password) {
        mUserId=UUID.randomUUID();
        mUserName = userName;
        mPassword = password;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}