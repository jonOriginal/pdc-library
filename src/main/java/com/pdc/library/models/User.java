package com.pdc.library.models;

public class User {
    private int userID;
    private String userName;

    public User() {
        this.userID = 0;
        this.userName = "";
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int newUserID) {
        this.userID = newUserID;
    }

    public String getName() {
        return userName;
    }

    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }
}
