package com.pdc.library.view.book;

import com.pdc.library.models.Book;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class BookDisplay extends JPanel {

    public BookDisplay(Book book, Consumer<Integer> deleteAction, Consumer<Book> editAction, Consumer<Book> viewLoansAction) {
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        var idLabel = new JLabel("Id: " + book.getId());
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));


        var titleLabel = new JLabel(book.getName());
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        var authorLabel = new JLabel("By: " + book.getAuthor());
        authorLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        var deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> onDeleteClicked(book, deleteAction));
        deleteButton.setForeground(Color.RED);

        var editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            editAction.accept(book);
        });

        var viewLoansButton = new JButton("View Loan");
        viewLoansButton.addActionListener(e -> {
            viewLoansAction.accept(book);
        });

        add(idLabel);
        add(titleLabel);
        add(authorLabel);
        add(deleteButton);
        add(editButton);
        add(viewLoansButton);
    }

    private void onDeleteClicked(Book book, Consumer<Integer> deleteAction) {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            deleteAction.accept(book.getId());
        }
    }

}
