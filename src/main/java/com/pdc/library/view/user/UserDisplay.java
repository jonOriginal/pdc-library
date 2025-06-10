package com.pdc.library.view.user;

import com.pdc.library.models.User;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class UserDisplay extends JPanel {

    public UserDisplay(User user, Consumer<User> deleteAction, Consumer<User> editAction, Consumer<User> viewLoansAction) {
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        var idLabel = new JLabel("Id: " + user.getId());
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));

        var titleLabel = new JLabel(user.getName());
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        var deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteAction.accept(user));
        deleteButton.setForeground(Color.RED);

        var editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            editAction.accept(user);
        });

        var viewLoansButton = new JButton("View Loans");
        viewLoansButton.addActionListener(e -> {
            viewLoansAction.accept(user);
        });

        editButton.setForeground(Color.BLUE);
        add(idLabel);
        add(titleLabel);
        add(deleteButton);
        add(editButton);
        add(viewLoansButton);
    }


}