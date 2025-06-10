package com.pdc.library.models;

public class User {
    private final int Id;
    private String Name;

    public User(int id, String name) {
        this.Id = id;
        this.Name = name;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
