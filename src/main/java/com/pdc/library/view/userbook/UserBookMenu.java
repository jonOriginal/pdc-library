package com.pdc.library.view.userbook;

import com.pdc.library.db.interfaces.LibRepository;
import com.pdc.library.models.UserBook;
import com.pdc.library.util.Listener;
import com.pdc.library.view.Menu;
import com.pdc.library.view.MenuAction;
import com.pdc.library.view.SearchBar;
import com.pdc.library.view.user.UserDisplay;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class UserBookMenu extends Menu {
    private final LibRepository repo;
    private final JPanel userBookPanel;

    public UserBookMenu(Listener<MenuAction> menuListener, LibRepository repository) {
        super(menuListener);
        this.repo = repository;

        this.setLayout(new BorderLayout());
        this.userBookPanel = new JPanel();
        userBookPanel.setLayout(new BoxLayout(userBookPanel, BoxLayout.Y_AXIS));

        var scrollBox = new JScrollPane(userBookPanel);
        scrollBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBox.setAlignmentX(LEFT_ALIGNMENT);

        List<UserBookDisplay> userBookPanels = null;
        try {
            userBookPanels = getUserBookPanel(repository.findAllUserBooks());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        var controlsPanel = getControlsPanel();

        this.add(controlsPanel, BorderLayout.NORTH);
        this.add(scrollBox, BorderLayout.CENTER);
        setUserBooks(userBookPanels);
    }

    private JPanel getControlsPanel() {
        var controlsPanel = new JPanel();

        var backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            listener.onEvent(MenuAction.HOME);
        });

        var addButton = new JButton("Lend Book");
        addButton.addActionListener(e -> showAddUserDialog());

        var findOverdueButton = new JButton("Find Overdue");
        findOverdueButton.addActionListener(e -> findOverdueBooks());



        controlsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.add(backButton);
        controlsPanel.add(addButton);
        controlsPanel.add(findOverdueButton);

        return controlsPanel;
    }

    private void findOverdueBooks() {
        try {
            var overdueBooks = repo.findOverdueBooks();
            if (overdueBooks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No overdue books found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                var overdueBookDisplays = getUserBookPanel(overdueBooks);
                setUserBooks(overdueBookDisplays);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching overdue books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddUserDialog() {
        var dialog = new LendBookDialog(this::addUserBook);
        dialog.setVisible(true);
    }

    private void clear() {
        try {
            var userBookDisplays = getUserBookPanel(repo.findAllUserBooks());
            setUserBooks(userBookDisplays);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching userBooks: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setUserBooks(List<UserBookDisplay> userBookPanels) {
        userBookPanel.removeAll();
        for (var panel : userBookPanels) {
            var pref = panel.getPreferredSize();
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, pref.height));
            panel.setAlignmentX(Component.LEFT_ALIGNMENT);
            userBookPanel.add(panel);
        }
        userBookPanel.revalidate();
        userBookPanel.repaint();
    }

    private void showEditUserDialog(UserBook userBook) {
        var dialog = new EditUserBookDialog(this::editUser, userBook);
        dialog.setVisible(true);
    }

    private void editUser(UserBook userBook) {
        try {
            repo.updateUserBook(userBook);
            clear();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while updating the userBook: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDeleteDialog(UserBook userBook) {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this userBook?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            deleteUserBook(userBook);
        }
    }

    private void addUserBook(UserBook userBook) {
        try {
            repo.addUserBook(userBook);
            clear();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while adding the userBook: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUserBook(UserBook userBook) {
        try {
            repo.removeUserBook(userBook.getBookId());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while deleting the userBook: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        clear();
    }

    private List<UserBookDisplay> getUserBookPanel(Collection<UserBook> userBooks) {
        return userBooks.stream()
                .map(u -> new UserBookDisplay(u, this::showDeleteDialog, this::showEditUserDialog))
                .toList();
    }
}
