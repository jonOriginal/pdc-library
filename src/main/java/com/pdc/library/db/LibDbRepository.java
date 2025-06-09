package com.pdc.library.db;

import com.pdc.library.db.interfaces.LibRepository;
import com.pdc.library.models.Book;
import com.pdc.library.models.User;
import com.pdc.library.models.UserBook;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

public class LibDbRepository implements LibRepository {
    private final Connection connection;

    public LibDbRepository(Connection connection) {
        this.connection = connection;
    }

    public void createUserBookTable() {
        // Implementation for creating the user_book table in the database
        Statement statement = connection.createStatement();
        statement.execute(LibSql.CREATE_USER_BOOK_TABLE);
    }

    public void createBookTable() {
        // Implementation for creating the book table in the database
        Statement statement = connection.createStatement();
        statement.execute(LibSql.CREATE_BOOK_TABLE);
    }

    public void createUserTable() {
        // Implementation for creating the user table in the database
        Statement statement = connection.createStatement();
        statement.execute(LibSql.CREATE_USER_TABLE);
    }

    @Override
    public void addUserBook(UserBook userBook) {
        String insertSQL = "INSERT INTO " + LibSql.USER_BOOK_TABLE_NAME + "(UserID, BookID, DateHired) VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.PreparedStatement(insertSQL);

        // Inserted values:
        pstmt.setInt(1, userBook.getUserId());
        pstmt.setInt(2, userBook.getBookId());
        pstmt.setLocalDate(3, userBook.getDateHired());
        pstmt.executeUpdate();
    }

    @Override
    public void removeUserBook(UserBook userBook) {
        String deleteSQL = "DELETE FROM " + LibSql.USER_BOOK_TABLE_NAME + " WHERE UserID = '?' AND BookID = '?'";
        PreparedStatement pstmt = connection.PreparedStatement(deleteSQL);
        pstmt.userBook.getUserId();
        pstmt.userBook.getBookId();
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
    public void addBook(Book book) {
        String insertSQL = "INSERT INTO " + LibSql.BOOK_TABLE_NAME + "(BookID, BookAuthor, BookName) VALUES(?, ?, ?)";
        PreparedStatement pstmt = connection.PreparedStatement(insertSQL);

        // Inserted values:
        pstmt.setInt(1, book.getId());
        pstmt.setString(2, book.getBookAuthor());
        pstmt.setString(3, book.getBookName());
        pstmt.executeUpdate();
    }

    @Override
    public void removeBook(Book book) {
        String deleteSQL = "DELETE FROM " + LibSql.BOOK_TABLE_NAME + " WHERE BookID = '?'";
        PreparedStatement pstmt = connection.PreparedStatement(deleteSQL);
        pstmt.book.getId();
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
    public void addUser(User user) {
        String insertSQL = "INSERT INTO " + LibSql.USER_TABLE_NAME + "(UserID, UserName) VALUES(?, ?)";
        PreparedStatement pstmt = connection.PreparedStatement(insertSQL);

        // Inserted values:
        pstmt.setInt(1, user.getUserId());
        pstmt.setString(2, user.getName());
        pstmt.executeUpdate();
    }

    @Override
    public void removeUser(User user) {
        String deleteSQL = "DELETE FROM " + LibSql.USER_TABLE_NAME + " WHERE UserID = '?'";
        PreparedStatement pstmt = connection.PreparedStatement(deleteSQL);
        pstmt.user.getId();
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
}
