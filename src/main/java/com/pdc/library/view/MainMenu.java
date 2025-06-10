package com.pdc.library.view;

import com.pdc.library.util.Listener;

import javax.swing.*;

public class MainMenu extends Menu {
    public MainMenu(Listener<MenuAction> listener) {
        super(listener);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> listener.onEvent(MenuAction.EXIT));

        JButton usersButton = new JButton("Users");
        usersButton.addActionListener(e -> listener.onEvent(MenuAction.USERS));

        JButton booksButton = new JButton("Books");
        booksButton.addActionListener(e -> listener.onEvent(MenuAction.BOOKS));

        JButton loansButton = new JButton("Loans");
        loansButton.addActionListener(e -> listener.onEvent(MenuAction.LOANS));

        this.add(exitButton);
        this.add(usersButton);
        this.add(booksButton);
        this.add(loansButton);
    }
}
