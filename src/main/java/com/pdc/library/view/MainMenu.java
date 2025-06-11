package com.pdc.library.view;

import com.pdc.library.util.Listener;
import com.pdc.library.util.Navigate;

import javax.swing.*;

public class MainMenu extends Menu {
    public MainMenu(Listener<Navigate> listener) {
        super(listener);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> listener.onEvent(Navigate.targetless(MenuAction.EXIT)));

        JButton usersButton = new JButton("Users");
        usersButton.addActionListener(e -> listener.onEvent(Navigate.targetless(MenuAction.USERS)));

        JButton booksButton = new JButton("Books");
        booksButton.addActionListener(e -> listener.onEvent(Navigate.targetless(MenuAction.BOOKS)));

        JButton loansButton = new JButton("Loans");
        loansButton.addActionListener(e -> listener.onEvent(Navigate.targetless(MenuAction.LOANS)));

        this.add(exitButton);
        this.add(usersButton);
        this.add(booksButton);
        this.add(loansButton);
    }
}
