package com.pdc.library.view.userbook;

import com.pdc.library.models.UserBook;
import com.pdc.library.util.Validation;

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

        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        var bookIdField = new JTextField(20);
        var userIdField = new JTextField(20);
        var allowedDaysField = new JTextField(20);

        panel.add(new JLabel("Book ID:"));
        panel.add(bookIdField);
        panel.add(new JLabel("User ID:"));
        panel.add(userIdField);
        panel.add(new JLabel("Allowed Days:"));
        panel.add(allowedDaysField);

        var lendButton = new JButton("Lend Book");
        lendButton.addActionListener(e -> submit(bookIdField, userIdField, allowedDaysField));

        panel.add(lendButton);
        this.add(panel);
    }

    private void submit(JTextField bookIdField, JTextField userIdField, JTextField allowedDaysField) throws NumberFormatException {
        if (bookIdField.getText().isEmpty() || userIdField.getText().isEmpty() || allowedDaysField.getText().isEmpty()) {
            showInvalidInputMessage("Please fill in all fields.");
            return;
        }

        Validation.ensureInteger(bookIdField.getText());
        Validation.ensureInteger(userIdField.getText());
        Validation.ensureInteger(allowedDaysField.getText());

        var ub = UserBook.create(
                Integer.parseInt(bookIdField.getText()),
                Integer.parseInt(userIdField.getText()),
                Integer.parseInt(allowedDaysField.getText()));
        if (addUserBook != null) {
            addUserBook.accept(ub);
            this.dispose();
        }
    }

    private void showInvalidInputMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
}
