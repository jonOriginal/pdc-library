package com.pdc.library.view.book;

import com.pdc.library.models.Book;

import javax.swing.*;
import java.util.function.Consumer;

public class EditBookDialog extends JDialog {
    public EditBookDialog(Consumer<Book> onBookEdited, Book book) {
        setTitle("Edit Book");
        setModal(true);
        setSize(300, 200);
        setLocationRelativeTo(null);

        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        var nameField = new JTextField(20);
        nameField.setText(book.getName());

        var authorField = new JTextField(20);
        authorField.setText(book.getAuthor());

        var addButton = new JButton("Save");

        addButton.addActionListener(e -> {
            onSubmit(onBookEdited, nameField, authorField, book);
        });

        panel.add(new JLabel("ID:" + book.getId()));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(addButton);
        add(panel);
    }

    private void onSubmit(Consumer<Book> onBookEdited, JTextField nameField, JTextField authorField, Book book) {
        var name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        book.setName(name);
        book.setAuthor(authorField.getText());
        onBookEdited.accept(book);
        dispose();
    }
}
