package com.pdc.library.view.user;

import com.pdc.library.models.User;

import javax.swing.*;
import java.util.function.Consumer;

public class EditUserDialog extends JDialog {
    public EditUserDialog(Consumer<User> onUserEdited, User user) {
        setTitle("Edit User");
        setModal(true);
        setSize(300, 200);
        setLocationRelativeTo(null);

        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        var nameField = new JTextField(20);
        nameField.setText(user.getName());

        var addButton = new JButton("Save");

        addButton.addActionListener(e -> {
            onSubmit(onUserEdited, nameField, user);
        });

        panel.add(new JLabel("ID:" + user.getId()));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(addButton);
        add(panel);
    }

    private void onSubmit(Consumer<User> onUserEdited, JTextField nameField, User user) {
        var name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        user.setName(name);
        onUserEdited.accept(user);
        dispose();
    }
}
