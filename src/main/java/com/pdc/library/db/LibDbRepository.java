package com.pdc.library.db;

import com.pdc.library.db.interfaces.LibRepository;
import com.pdc.library.models.Book;
import com.pdc.library.models.User;
import com.pdc.library.models.UserBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class LibDbRepository implements LibRepository {
    private final Connection connection;

    public LibDbRepository(Connection connection) {
        this.connection = connection;
    }

    private List<Book> dummyBooks() {
        return List.of(
                new Book(1, "Author A", "Book A"),
                new Book(2, "Author B", "Book B"),
                new Book(3, "Author C", "Book C")
        );
    }

    private List<User> dummyUsers() {
        return List.of(
                new User(1, "User A"),
                new User(2, "User B"),
                new User(3, "User C")
        );
    }

    private List<UserBook> dummyUserBooks() {
        return List.of(
                new UserBook(1, 1),
                new UserBook(2, 2),
                new UserBook(3, 3)
        );
    }

    public void createUserBookTable() throws SQLException {
        var statement = connection.createStatement();
        try {
            statement.execute(LibSql.CREATE_USER_BOOK_TABLE);
            for (var userBook : dummyUserBooks()) {
                addUserBook(userBook);
            }
        } catch (SQLException e) {
            if (Objects.equals(e.getSQLState(), "X0Y32")) {
                return;
            }
            throw e;
        }
    }

    public void createBookTable() throws SQLException {
        var statement = connection.createStatement();
        try {
            statement.execute(LibSql.CREATE_BOOK_TABLE);
            for (var book : dummyBooks()) {
                addBook(book);
            }
        } catch (SQLException e) {
            if (Objects.equals(e.getSQLState(), "X0Y32")) {
                return;
            }
            throw e;
        }
    }

    public void createUserTable() throws SQLException {
        var statement = connection.createStatement();
        try {
            statement.execute(LibSql.CREATE_USER_TABLE);
            for (var user : dummyUsers()) {
                addUser(user);
            }
        } catch (SQLException e) {
            if (Objects.equals(e.getSQLState(), "X0Y32")) {
                return;
            }
            throw e;
        }
    }

    @Override
    public void addUserBook(UserBook userBook) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.INSERT_USER_BOOK);
        pstmt.setInt(1, userBook.getUserId());
        pstmt.setInt(2, userBook.getBookId());
        pstmt.setDate(3, userBook.getDateHired());
        pstmt.executeUpdate();
    }

    @Override
    public void removeUserBook(int bookId) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.DELETE_USER_BOOK);
        pstmt.setInt(1, bookId);
        pstmt.executeUpdate();
    }

    @Override
    public Collection<UserBook> findUserBooksByUserId(int userId) {
        return List.of();
    }

    @Override
    public UserBook findUserBookByBookId(int bookId) {
        return null;
    }

    @Override
    public Collection<UserBook> findOverdueBooks() {
        return List.of();
    }

    @Override
    public Collection<UserBook> findOverdueBooksByUserId(int userId) {
        return List.of();
    }

    @Override
    public void addBook(Book book) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.INSERT_BOOK);
        pstmt.setInt(1, book.getId());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getName());
        pstmt.executeUpdate();
    }

    @Override
    public void removeBook(int bookId) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.DELETE_BOOK);
        pstmt.setInt(1, bookId);
        pstmt.executeUpdate();
    }

    @Override
    public Book findBookById(int id) {
        return null;
    }

    @Override
    public Collection<Book> findBookByTitle(String title) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.FIND_BOOK_BY_TITLE);
        pstmt.setString(1, "%" + title + "%");
        return getBooks(pstmt);
    }

    private Collection<Book> getBooks(PreparedStatement pstmt) throws SQLException {
        var resultSet = pstmt.executeQuery();
        List<Book> books = new java.util.ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("BookID");
            String author = resultSet.getString("BookAuthor");
            String name = resultSet.getString("BookName");
            books.add(new Book(id, author, name));
        }
        return books;
    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void addUser(User user) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.INSERT_USER);
        pstmt.setInt(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.executeUpdate();
    }

    @Override
    public void removeUser(int userId) throws SQLException {
        String deleteSQL = "DELETE FROM " + LibSql.USER_TABLE_NAME + " WHERE UserID = '?'";
        var pstmt = connection.prepareStatement(deleteSQL);
        pstmt.setInt(1, userId);
        pstmt.executeUpdate();
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public Collection<User> findUserByName(String name) {
        return List.of();
    }

    @Override
    public Collection<Book> findAllBooks() {
        try {
            String selectSQL = "SELECT * FROM " + LibSql.BOOK_TABLE_NAME;
            var pstmt = connection.prepareStatement(selectSQL);
            return getBooks(pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
