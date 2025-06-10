package com.pdc.library.view;

import com.pdc.library.util.Listener;
import com.pdc.library.util.Navigate;

import javax.swing.*;

public class Menu extends JPanel {
    protected Listener<Navigate> listener;

    public Menu(Listener<Navigate> listener) {
        this.listener = listener;
    }
}
