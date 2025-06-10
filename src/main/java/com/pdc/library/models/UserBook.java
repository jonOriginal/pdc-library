package com.pdc.library.models;

import java.sql.Date;
import java.time.LocalDate;

public class UserBook {
    private final int UserId;
    private final int BookId;
    private Book book;
    private User user;
    private Date dateHired;

    public UserBook(int userId, int bookId) {
        this.UserId = userId;
        this.BookId = bookId;
    }

    public int getUserId() {
        return UserId;
    }

    public int getBookId() {
        return BookId;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public Date getDateHired() {
        return dateHired;
    }
}
