package com.pdc.library.view.book;

import com.pdc.library.models.Book;
import com.pdc.library.util.Validation;

import javax.swing.*;
import java.util.function.Consumer;

public class AddBookDialog extends JDialog {

    public AddBookDialog(Consumer<Book> onBookAdded) {
        setTitle("Add Book");
        setModal(true);
        setSize(300, 200);
        setLocationRelativeTo(null);

        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        var idField = new JTextField(20);
        var titleField = new JTextField(20);
        var authorField = new JTextField(20);

        var addButton = new JButton("Add Book");

        addButton.addActionListener(e -> {
            onSubmit(onBookAdded, titleField, authorField, idField);
        });

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(addButton);

        add(panel);
    }

    private void onSubmit(Consumer<Book> onBookAdded, JTextField titleField, JTextField authorField, JTextField idField) throws NumberFormatException {
        var title = titleField.getText();
        var author = authorField.getText();
        var id = idField.getText();

        Validation.ensureInteger(id);

        if (title.isEmpty() || author.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        onBookAdded.accept(
                new Book(Integer.parseInt(id), title, author)
        );
        dispose();
    }
}
