package com.pdc.library.models;

public class Book {
    private int Id;
    private String author;
    private String name;

    public Book(int id, String author, String name) {
        this.Id = id;
        this.author = author;
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
