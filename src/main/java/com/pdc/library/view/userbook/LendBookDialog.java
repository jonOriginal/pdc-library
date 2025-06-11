package com.pdc.library.view.userbook;

import com.pdc.library.models.UserBook;

import javax.swing.*;
import java.util.function.Consumer;

public class LendBookDialog extends JDialog {
    private final Consumer<UserBook> addUserBook;

    public LendBookDialog(Consumer<UserBook> addUserBook) {
        this.addUserBook = addUserBook;
        setTitle("Lend Book");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField bookIdField = new JTextField(20);
        JTextField userIdField = new JTextField(20);
        JTextField allowedDaysField = new JTextField(20);

        panel.add(new JLabel("Book ID:"));
        panel.add(bookIdField);
        panel.add(new JLabel("User ID:"));
        panel.add(userIdField);
        panel.add(new JLabel("Allowed Days:"));
        panel.add(allowedDaysField);

        JButton lendButton = new JButton("Lend Book");
        lendButton.addActionListener(e -> submit(bookIdField, userIdField, allowedDaysField));

        panel.add(lendButton);
        this.add(panel);
    }

    private void submit(JTextField bookIdField, JTextField userIdField, JTextField allowedDaysField) {
        if (bookIdField.getText().isEmpty() || userIdField.getText().isEmpty() || allowedDaysField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Integer.parseInt(bookIdField.getText());
            Integer.parseInt(userIdField.getText());
            Integer.parseInt(allowedDaysField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Book ID, User ID, and Allowed Days.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var ub = UserBook.create(
                Integer.parseInt(bookIdField.getText()),
                Integer.parseInt(userIdField.getText()),
                Integer.parseInt(allowedDaysField.getText()));
        if (addUserBook != null) {
            addUserBook.accept(ub);
            this.dispose();
        }
    }
}
