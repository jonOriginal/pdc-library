package com.pdc.library.view.userbook;

import com.pdc.library.models.UserBook;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.function.Consumer;

public class UserBookDisplay extends JPanel {
    public UserBookDisplay(UserBook userBook, Consumer<UserBook> deleteAction, Consumer<UserBook> editAction) {

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        var bookIdLabel = new JLabel("Book ID: " + userBook.getBookId());
        bookIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        var bookNameLabel = new JLabel("Title: " + userBook.getBookTitle());
        bookNameLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        var userIdLabel = new JLabel("User ID: " + userBook.getUserId());
        userIdLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        var userNameLabel = new JLabel("Name: " + userBook.getUserName());
        userNameLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        var dateLabel = new JLabel("Borrow Date: " + userBook.getDateHired().toString());
        dateLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        var c = Calendar.getInstance();
        c.setTime(userBook.getDateHired());
        c.add(Calendar.DATE, userBook.getAllowedDays());
        var returnDate = c.getTime();
        var formattedReturnDate = String.format("%1$td/%1$tm/%1$tY", returnDate);

        var returnDateLabel = new JLabel("Return Date: " + formattedReturnDate);
        returnDateLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        if (returnDate.after(Calendar.getInstance().getTime())) {
            returnDateLabel.setForeground(Color.RED);
        }

        var deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteAction.accept(userBook));
        deleteButton.setForeground(Color.RED);

        var editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            editAction.accept(userBook);
        });

        editButton.setForeground(Color.BLUE);
        add(bookIdLabel);
        add(bookNameLabel);

        add(userIdLabel);
        add(userNameLabel);

        add(dateLabel);
        add(returnDateLabel);

        add(deleteButton);
        add(editButton);
    }
}