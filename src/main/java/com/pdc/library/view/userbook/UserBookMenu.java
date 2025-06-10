package com.pdc.library.view.userbook;

import com.pdc.library.db.interfaces.LibRepository;
import com.pdc.library.models.UserBook;
import com.pdc.library.util.Listener;
import com.pdc.library.util.Navigate;
import com.pdc.library.view.Menu;
import com.pdc.library.view.MenuAction;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class UserBookMenu extends Menu {
    private final LibRepository repo;
    private final JPanel userBookPanel;
    private final JLabel searchLabel;

    public UserBookMenu(Listener<Navigate> menuListener, LibRepository repository, String path) {
        super(menuListener);
        this.repo = repository;
        this.searchLabel = new JLabel();
        this.setLayout(new BorderLayout());
        this.userBookPanel = new JPanel();
        userBookPanel.setLayout(new BoxLayout(userBookPanel, BoxLayout.Y_AXIS));

        var scrollBox = new JScrollPane(userBookPanel);
        scrollBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBox.setAlignmentX(LEFT_ALIGNMENT);

        var controlsPanel = getControlsPanel();
        this.add(controlsPanel, BorderLayout.NORTH);
        this.add(scrollBox, BorderLayout.CENTER);

        if (path != null && !path.isEmpty()) {
            NavigateTo(path);
        } else {
            clear();
        }
    }

    private void NavigateTo(String path) {
        var firstPart = path.split("/")[0];
        var id = path.split("/").length > 1 ? path.split("/")[1] : null;
        try {
            if (id != null) {
                Integer.parseInt(id);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (firstPart.equals("users")) {
            if (id != null) {
                filterByUser(Integer.parseInt(id));
            } else {
                listener.onEvent(Navigate.targetless(MenuAction.USERS));
            }
        } else if (firstPart.equals("books")) {
            if (id != null) {
                filterByBook(Integer.parseInt(id));
            } else {
                listener.onEvent(Navigate.targetless(MenuAction.BOOKS));
            }
        }
    }

    private void filterByUser(int userId) {
        try {
            var userBooks = repo.findUserBooksByUserId(userId);
            if (userBooks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No loans found for this user.", "Info", JOptionPane.INFORMATION_MESSAGE);
                clear();
            } else {
                var userBookDisplays = getUserBookPanel(userBooks);
                setUserBooks(userBookDisplays);
                searchLabel.setText("Showing loans for user ID: " + userId);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching user books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid User ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterByBook(int bookId) {
        try {
            var userBook = repo.findUserBookByBookId(bookId);
            if (userBook == null) {
                JOptionPane.showMessageDialog(this, "No loans found for this book.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                var userBookDisplays = getUserBookPanel(List.of(userBook));
                setUserBooks(userBookDisplays);
                searchLabel.setText("Showing loans for book ID: " + bookId);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching user books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel getControlsPanel() {
        var controlsPanel = new JPanel();

        var backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            listener.onEvent(Navigate.targetless(MenuAction.HOME));
        });

        var addButton = new JButton("Lend Book");
        addButton.addActionListener(e -> showAddUserDialog());

        var findOverdueButton = new JButton("Find Overdue");
        findOverdueButton.addActionListener(e -> findOverdueBooks());

        searchLabel.setText("Showing all active loans");

        controlsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.add(backButton);
        controlsPanel.add(addButton);
        controlsPanel.add(findOverdueButton);
        controlsPanel.add(searchLabel);

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
                searchLabel.setText("Showing overdue loans");
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
            searchLabel.setText("Showing all active loans");
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
