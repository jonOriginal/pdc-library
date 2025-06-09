package com.pdc.library.models;

import java.time.LocalDate;

public class UserBook {
    private int userID;
    private int bookID;
    private LocalDate dateHired;

    public UserBook(int userID, int bookID) {
        this.userID = getUserID();
        this.bookID = getBookID();
    }
    
    public LocalDate getDateHired() {
        return dateHired;
    }

    public void setDateHired(LocalDate newDateHired) {
        this.dateHired = newDateHired;
    }
}
