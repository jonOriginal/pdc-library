package com.pdc.library.models;

import java.sql.Date;
import java.time.LocalDate;

public class UserBook {
    private final int BookId;
    private String bookTitle;
    private String bookAuthor;

    private final int UserId;
    private String userName;

    private Date dateHired;
    private int allowedDays;

    public UserBook(int userId, int bookId, Date dateHired, int allowedDays, String bookTitle, String bookAuthor, String userName) {
        this.UserId = userId;
        this.BookId = bookId;
        this.dateHired = dateHired;
        this.allowedDays = allowedDays;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.userName = userName;
    }

    public static UserBook create(int userId, int bookId, int allowedDays) {
        return new UserBook(userId, bookId, Date.valueOf(LocalDate.now()), allowedDays, null, null, null);
    }

    public int getUserId() {
        return UserId;
    }

    public int getBookId() {
        return BookId;
    }

    public Date getDateHired() {
        return dateHired;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getUserName() {
        return userName;
    }

    public int getAllowedDays() {
        return allowedDays;
    }
}
