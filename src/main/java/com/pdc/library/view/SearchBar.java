package com.pdc.library.view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class SearchBar extends JPanel {

    public SearchBar(String label, String placeholder, Consumer<String> searchAction, Runnable clearAction) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        var searchLabel = new JLabel(label);
        this.add(searchLabel);

        var searchField = getSearchField(placeholder);
        this.add(searchField);

        var searchButton = getSearchButton(searchAction, searchField);
        this.add(searchButton);

        var clearButton = getClearButton(clearAction, searchField);
        this.add(clearButton);
    }

    private static JButton getClearButton(Runnable clearAction, JTextField searchField) {
        var clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            searchField.setText("");
            clearAction.run();
        });
        return clearButton;
    }

    private static JTextField getSearchField(String placeholder) {
        var searchField = new JTextField(20);
        searchField.setToolTipText(placeholder);
        return searchField;
    }

    private JButton getSearchButton(Consumer<String> searchAction, JTextField searchField) {
        var searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            var query = searchField.getText().trim();
            if (!query.isEmpty()) {
                searchAction.accept(query);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        return searchButton;
    }


}
