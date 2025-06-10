package com.pdc.library.db;

import com.pdc.library.db.interfaces.LibRepository;
import com.pdc.library.models.Book;
import com.pdc.library.models.User;
import com.pdc.library.models.UserBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class LibDbRepository implements LibRepository {
    private final Connection connection;

    public LibDbRepository(Connection connection) {
        this.connection = connection;
    }

    private static List<User> parseUsers(ResultSet resultSet) throws SQLException {
        var users = new java.util.ArrayList<User>();
        while (resultSet.next()) {
            var id = resultSet.getInt("UserID");
            var name = resultSet.getString("UserName");
            users.add(new User(id, name));
        }
        return users;
    }

    private static List<UserBook> filterOverdueBooks(Collection<UserBook> ubs) {
        return ubs.stream()
                .filter(userBook -> {
                    var c = java.util.Calendar.getInstance();
                    c.setTime(userBook.getDateHired());
                    c.add(java.util.Calendar.DATE, userBook.getAllowedDays());
                    return c.getTime().after(java.util.Calendar.getInstance().getTime());
                })
                .toList();
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
                UserBook.create(1, 1, 7),
                UserBook.create(2, 2, 7),
                UserBook.create(3, 3, 7)
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
    public void updateUserBook(UserBook userBook) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.UPDATE_USER_BOOK);
        pstmt.setInt(1, userBook.getUserId());
        pstmt.setInt(2, userBook.getBookId());
        pstmt.setDate(3, userBook.getDateHired());
        pstmt.setInt(4, userBook.getAllowedDays());
        pstmt.executeUpdate();
    }

    @Override
    public Collection<UserBook> findUserBooksByUserId(int userId) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.SELECT_USER_BOOKS_BY_USER_ID);
        pstmt.setInt(1, userId);
        var resultSet = pstmt.executeQuery();
        return parseUserBooks(resultSet);
    }

    private Collection<UserBook> parseUserBooks(ResultSet resultSet) throws SQLException {
        var userBooks = new java.util.ArrayList<UserBook>();
        while (resultSet.next()) {
            int userId = resultSet.getInt("UserID");
            int bookId = resultSet.getInt("BookID");
            var dateHired = resultSet.getDate("DateHired");
            int allowedDays = resultSet.getInt("AllowedDays");
            var bookTitle = resultSet.getString("BookName");
            var bookAuthor = resultSet.getString("BookAuthor");
            var userName = resultSet.getString("UserName");

            var userBook = new UserBook(userId, bookId, dateHired, allowedDays, bookTitle, bookAuthor, userName);
            userBooks.add(userBook);
        }
        return userBooks;
    }

    @Override
    public UserBook findUserBookByBookId(int bookId) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.SELECT_USER_BOOK_BY_BOOK_ID);
        pstmt.setInt(1, bookId);
        var resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            int userId = resultSet.getInt("UserID");
            var dateHired = resultSet.getDate("DateHired");
            int allowedDays = resultSet.getInt("AllowedDays");
            var bookTitle = resultSet.getString("BookName");
            var bookAuthor = resultSet.getString("BookAuthor");
            var userName = resultSet.getString("UserName");

            return new UserBook(userId, bookId, dateHired, allowedDays, bookTitle, bookAuthor, userName);
        }
        return null;
    }

    @Override
    public Collection<UserBook> findOverdueBooks() throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.SELECT_ALL_USER_BOOKS);
        var resultSet = pstmt.executeQuery();
        var uB = parseUserBooks(resultSet);
        if (uB.isEmpty()) {
            return List.of();
        }
        return filterOverdueBooks(uB);
    }

    @Override
    public Collection<UserBook> findAllUserBooks() throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.SELECT_ALL_USER_BOOKS);
        var resultSet = pstmt.executeQuery();
        return parseUserBooks(resultSet);
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
    public Book findBookById(int id) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.SELECT_BOOK_BY_ID);
        pstmt.setInt(1, id);
        var resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            var author = resultSet.getString("BookAuthor");
            var name = resultSet.getString("BookName");
            return new Book(id, author, name);
        }
        return null;
    }

    @Override
    public Collection<Book> findBookByTitle(String title) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.FIND_BOOK_BY_TITLE);
        pstmt.setString(1, "%" + title + "%");
        return parseBooks(pstmt);
    }

    private Collection<Book> parseBooks(PreparedStatement pstmt) throws SQLException {
        var resultSet = pstmt.executeQuery();
        var books = new java.util.ArrayList<Book>();
        while (resultSet.next()) {
            int id = resultSet.getInt("BookID");
            var author = resultSet.getString("BookAuthor");
            var name = resultSet.getString("BookName");
            books.add(new Book(id, author, name));
        }
        return books;
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.UPDATE_BOOK);
        pstmt.setString(1, book.getAuthor());
        pstmt.setString(2, book.getName());
        pstmt.setInt(3, book.getId());
        pstmt.executeUpdate();
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
        var pstmt = connection.prepareStatement(LibSql.DELETE_USER);
        pstmt.setInt(1, userId);
        pstmt.executeUpdate();
    }

    @Override
    public Collection<User> findAllUsers() throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.SELECT_ALL_USERS);
        var resultSet = pstmt.executeQuery();
        return parseUsers(resultSet);
    }

    @Override
    public void updateUser(User user) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.UPDATE_USER);
        pstmt.setString(1, user.getName());
        pstmt.setInt(2, user.getId());
        pstmt.executeUpdate();
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public Collection<User> findUserByName(String name) throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.FIND_USER_BY_NAME);
        pstmt.setString(1, "%" + name + "%");
        var resultSet = pstmt.executeQuery();
        return parseUsers(resultSet);
    }

    @Override
    public Collection<Book> findAllBooks() throws SQLException {
        var pstmt = connection.prepareStatement(LibSql.SELECT_ALL_BOOKS);
        return parseBooks(pstmt);
    }
}
