package com.pdc.library.view;

import com.pdc.library.util.Listener;

import javax.swing.*;

public class Menu extends JPanel {
    protected Listener<MenuAction> listener;

    public Menu(Listener<MenuAction> listener) {
        this.listener = listener;
    }
}
