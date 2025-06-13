package com.pdc.library.view.user;

import com.pdc.library.models.User;
import com.pdc.library.util.Validation;

import javax.swing.*;
import java.util.function.Consumer;

public class AddUserDialog extends JDialog {

    public AddUserDialog(Consumer<User> onUserAdded) {
        setTitle("Add User");
        setModal(true);
        setSize(300, 200);
        setLocationRelativeTo(null);

        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        var idField = new JTextField(20);
        var nameField = new JTextField(20);

        var addButton = new JButton("Add User");

        addButton.addActionListener(e -> {
            onSubmit(onUserAdded, idField, nameField);
        });

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(addButton);
        add(panel);
    }

    private void onSubmit(Consumer<User> onUserAdded, JTextField idField, JTextField nameField) throws NumberFormatException {
        var name = nameField.getText();
        var id = idField.getText();

        Validation.ensureInteger(id);

        if (name.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        onUserAdded.accept(
                new User(Integer.parseInt(id), name)
        );
        dispose();
    }
}
