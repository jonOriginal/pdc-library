package com.pdc.library.view.book;

import com.pdc.library.db.interfaces.BookRepository;
import com.pdc.library.models.Book;
import com.pdc.library.util.Listener;
import com.pdc.library.util.Navigate;
import com.pdc.library.view.Menu;
import com.pdc.library.view.MenuAction;
import com.pdc.library.view.SearchBar;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class BookMenu extends Menu {

    private final BookRepository repository;

    private final JPanel bookListPanel;

    public BookMenu(Listener<Navigate> menuListener, BookRepository repository) {
        super(menuListener);
        this.setLayout(new BorderLayout());
        this.repository = repository;
        this.bookListPanel = new JPanel();
        bookListPanel.setLayout(new BoxLayout(bookListPanel, BoxLayout.Y_AXIS));

        var scrollBox = new JScrollPane(bookListPanel);
        scrollBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBox.setAlignmentX(LEFT_ALIGNMENT);

        List<BookDisplay> bookPanels = null;
        try {
            bookPanels = getBookPanels(repository.findAllBooks());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        var controlsPanel = getControlsPanel();

        this.add(controlsPanel, BorderLayout.NORTH);
        this.add(scrollBox, BorderLayout.CENTER);
        setBooks(bookPanels);
    }

    private JPanel getControlsPanel() {
        var controlsPanel = new JPanel();

        var backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            listener.onEvent(Navigate.targetless(MenuAction.HOME));
        });

        var addButton = new JButton("Add Book");
        addButton.addActionListener(e -> showAddBookDialog());

        var searchBar = new SearchBar(
                "Search Books",
                "Search by book name or author",
                this::onSearch,
                this::refreshBooks
        );

        controlsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.add(backButton);
        controlsPanel.add(searchBar);
        controlsPanel.add(addButton);
        return controlsPanel;
    }

    private void showAddBookDialog() {
        var addBookDialog = new AddBookDialog(this::onAdded);
        addBookDialog.setVisible(true);
    }

    private void deleteBook(int bookId) {
        try {
            repository.removeBook(bookId);
            refreshBooks();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23503")) {
                JOptionPane.showMessageDialog(this, "Cannot delete book. It is currently borrowed by a user.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "An error occurred while deleting the book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editBook(Book book) {
        var editBookDialog = new EditBookDialog(this::onEdited, book);
        editBookDialog.setVisible(true);
    }

    private void onEdited(Book book) {
        try {
            repository.updateBook(book);
            refreshBooks();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while refreshing the book list: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAdded(Book book) {
        try {
            repository.addBook(book);
            refreshBooks();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while adding the book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onViewLoans(Book book) {
        listener.onEvent(new Navigate(MenuAction.LOANS, "books/" + book.getId()));
    }

    private List<BookDisplay> getBookPanels(Collection<Book> books) {
        return books.stream()
                .map(book -> new BookDisplay(book, this::deleteBook, this::editBook, this::onViewLoans))
                .toList();
    }

    private void setBooks(java.util.List<BookDisplay> bookPanels) {
        bookListPanel.removeAll();
        for (var bookPanel : bookPanels) {
            var pref = bookPanel.getPreferredSize();
            bookPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, pref.height));
            bookPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            bookListPanel.add(bookPanel);
        }
        bookListPanel.revalidate();
        bookListPanel.repaint();
    }

    private void refreshBooks() {
        try {
            var bookPanels = getBookPanels(repository.findAllBooks());
            setBooks(bookPanels);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while clearing the search: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSearch(String query) {
        try {
            var bookPanels = getBookPanels(repository.findBookByTitleOrAuthor(query));
            if (bookPanels.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No books found for the search term: " + query, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                setBooks(bookPanels);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while searching for books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
