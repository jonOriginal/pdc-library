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
        // Implementation for creating the user_book table in the database
        Statement statement = connection.createStatement();
        if (statement.execute(LibSql.CREATE_USER_BOOK_TABLE)) {
            for (var userBook : dummyUserBooks()) {
                addUserBook(userBook);
            }
        } else {
            System.out.println("User book table already exists.");
        }
    }

    public void createBookTable() throws SQLException {
        // Implementation for creating the book table in the database
        Statement statement = connection.createStatement();
        if (statement.execute(LibSql.CREATE_BOOK_TABLE)) {
            for (var book : dummyBooks() ) {
                addBook(book);
            }
        } else {
            System.out.println("Book table already exists.");
        }
    }

    public void createUserTable() throws SQLException {
        // Implementation for creating the user table in the database
        Statement statement = connection.createStatement();
        if (statement.execute(LibSql.CREATE_USER_TABLE)) {
            for (var user : dummyUsers()) {
                addUser(user);
            }
        } else {
            System.out.println("User table already exists.");
        }
    }

    @Override
    public void addUserBook(UserBook userBook) throws SQLException {
        String insertSQL = "";
        var pstmt = connection.prepareStatement(insertSQL);

        // Inserted values:
        pstmt.setInt(1, userBook.getUserId());
        pstmt.setInt(2, userBook.getBookId());
        pstmt.setDate(3, userBook.getDateHired());
        pstmt.executeUpdate();
    }

    @Override
    public void removeUserBook(int bookId) throws SQLException {
        String deleteSQL = "DELETE FROM " + LibSql.USER_BOOK_TABLE_NAME + " WHERE BookID = '?'";
        var pstmt = connection.prepareStatement(deleteSQL);
        pstmt.setInt(1,bookId);
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
        String insertSQL = "INSERT INTO " + LibSql.BOOK_TABLE_NAME + "(BookID, BookAuthor, BookName) VALUES(?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(insertSQL);

        // Inserted values:
        pstmt.setInt(1, book.getId());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getName());
        pstmt.executeUpdate();
    }

    @Override
    public void removeBook(int bookId) throws SQLException {
        String deleteSQL = "DELETE FROM " + LibSql.BOOK_TABLE_NAME + " WHERE BookID = '?'";
        PreparedStatement pstmt = connection.prepareStatement(deleteSQL);
        pstmt.setInt(1, bookId);
        pstmt.executeUpdate();
    }

    @Override
    public Book findBookById(int id) {
        return null;
    }

    @Override
    public Collection<Book> findBookByTitle(String title) {
        return List.of();
    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void addUser(User user) throws SQLException {
        String insertSQL = "INSERT INTO " + LibSql.USER_TABLE_NAME + "(UserID, UserName) VALUES(?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(insertSQL);

        // Inserted values:
        pstmt.setInt(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.executeUpdate();
    }

    @Override
    public void removeUser(int userId) throws SQLException {
        String deleteSQL = "DELETE FROM " + LibSql.USER_TABLE_NAME + " WHERE UserID = '?'";
        PreparedStatement pstmt = connection.prepareStatement(deleteSQL);
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
        return null;
    }
}
