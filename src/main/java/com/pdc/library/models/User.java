package com.pdc.library.models;

public class User {
    private final int Id;
    private String Name;

    public User() {
        this.Id = 0;
        this.Name = "";
    }

    public int getUserId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String newUserName) {
        this.Name = newUserName;
    }
}
