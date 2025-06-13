package com.pdc.library.view.userbook;

import com.pdc.library.models.UserBook;
import com.pdc.library.util.Validation;

import javax.swing.*;
import java.util.function.Consumer;

public class EditUserBookDialog extends JDialog {
    public EditUserBookDialog(Consumer<UserBook> editUserBook, UserBook userBook) {
        setTitle("Edit User Book");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);

        var panel = new JPanel();
        var bookIdLabel = new JLabel("Book ID: " + userBook.getBookId());
        var userIdLabel = new JLabel("User ID: " + userBook.getUserId());

        var durationLabel = new JLabel("Allowed Days:");
        var durationField = new JTextField(String.valueOf(userBook.getAllowedDays()), 20);

        var saveButton = new JButton("Save");
        saveButton.addActionListener(e -> submit(editUserBook, userBook, durationField));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(bookIdLabel);
        panel.add(userIdLabel);
        panel.add(durationLabel);
        panel.add(durationField);
        panel.add(saveButton);

        add(panel);
    }

    private void submit(Consumer<UserBook> editUserBook, UserBook userBook, JTextField durationField) {
        if (durationField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Validation.ensureInteger(durationField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Allowed days must be a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }

        userBook = new UserBook(userBook.getUserId(), userBook.getBookId(), userBook.getDateHired(), Integer.parseInt(durationField.getText()), userBook.getBookTitle(), userBook.getBookAuthor(), userBook.getUserName());
        editUserBook.accept(userBook);
        this.dispose();
    }
}
