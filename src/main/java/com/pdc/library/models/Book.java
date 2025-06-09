package com.pdc.library.models;

public class Book {
    private int bookID;
    private String bookAuthor;
    private String bookName;

    public Book() {
        this.bookID = 0;
        this.bookAuthor = "";
        this.bookName = "";
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int newBookID) {
       this.bookID = newBookID;
    }

    public String getBookAuthor() {
       return bookAuthor;
    }

    public void setBookAuthor(String newBookAuthor) {
        this.bookAuthor = newBookAuthor;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String newBookName) {
        this.bookName = newBookName;
    }
}
