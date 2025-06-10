package com.pdc.library.view.user;

import com.pdc.library.db.interfaces.UserRepository;
import com.pdc.library.models.User;
import com.pdc.library.util.Listener;
import com.pdc.library.view.Menu;
import com.pdc.library.view.MenuAction;
import com.pdc.library.view.SearchBar;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class UserMenu extends Menu {
    private final UserRepository repo;
    private final JPanel userListPanel;

    public UserMenu(Listener<MenuAction> listener, UserRepository repository) {
        super(listener);
        this.repo = repository;

        this.setLayout(new BorderLayout());
        this.userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));

        var scrollBox = new JScrollPane(userListPanel);
        scrollBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBox.setAlignmentX(LEFT_ALIGNMENT);

        List<UserDisplay> userPanels = null;
        try {
            userPanels = getUserPanels(repository.findAllUsers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        var controlsPanel = getControlsPanel();

        this.add(controlsPanel, BorderLayout.NORTH);
        this.add(scrollBox, BorderLayout.CENTER);
        setUsers(userPanels);
    }

    private void onSearch(String query) {
        try {
            var users = repo.findUserByName(query);
            setUsers(getUserPanels(users));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel getControlsPanel() {
        var controlsPanel = new JPanel();

        var backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            listener.onEvent(MenuAction.HOME);
        });

        var searchBar = new SearchBar(
                "Search Users",
                "Search by user name",
                this::onSearch,
                this::clear
        );

        var addButton = new JButton("Add User");
        addButton.addActionListener(e -> showAddUserDialog());

        controlsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.add(backButton);
        controlsPanel.add(searchBar);
        controlsPanel.add(addButton);

        return controlsPanel;
    }

    private void showAddUserDialog() {
        var dialog = new AddUserDialog(this::addUser);
        dialog.setVisible(true);
    }

    private void clear() {
        try {
            var userDisplays = getUserPanels(repo.findAllUsers());
            setUsers(userDisplays);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setUsers(List<UserDisplay> userPanels) {
        userListPanel.removeAll();
        for (var panel : userPanels) {
            var pref = panel.getPreferredSize();
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, pref.height));
            panel.setAlignmentX(Component.LEFT_ALIGNMENT);
            userListPanel.add(panel);
        }
        userListPanel.revalidate();
        userListPanel.repaint();
    }

    private void showEditUserDialog(User user) {
        var dialog = new EditUserDialog(this::editUser, user);
        dialog.setVisible(true);
    }

    private void editUser(User user) {
        try {
            repo.updateUser(user);
            clear();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while updating the user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDeleteDialog(User user) {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            deleteUser(user);
        }
    }

    private void addUser(User user) {
        try {
            repo.addUser(user);
            clear();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while adding the user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser(User user) {
        try {
            repo.removeUser(user.getId());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while deleting the user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        clear();
    }

    private List<UserDisplay> getUserPanels(Collection<User> users) {
        return users.stream()
                .map(user -> new UserDisplay(user, this::showDeleteDialog, this::showEditUserDialog))
                .toList();
    }


}
