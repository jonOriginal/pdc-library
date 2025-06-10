package com.pdc.library.db;

public class LibSql {
    private LibSql() {}

    public static final String BOOK_TABLE_NAME = "Books";
    public static final String USER_TABLE_NAME = "Users";
    public static final String USER_BOOK_TABLE_NAME = "UserBooks";

    public static final String CREATE_BOOK_TABLE =
            "CREATE TABLE " + BOOK_TABLE_NAME + " (" +
                    "BookID INT NOT NULL," +
                    "BookAuthor VARCHAR(255)," +       // Defaults to NULL
                    "BookName VARCHAR(255)," +         // Defaults to NULL
                    "PRIMARY KEY (BookID)" +
                    ")";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    "UserID INT NOT NULL," +
                    "UserName VARCHAR(255)," +         // Defaults to NULL
                    "PRIMARY KEY (UserID)" +
                    ")";


    public static final String CREATE_USER_BOOK_TABLE =
            "CREATE TABLE " + USER_BOOK_TABLE_NAME + " (" +
                    "UserID INT NOT NULL," +
                    "BookID INT NOT NULL," +
                    "DateHired DATE," +                // Defaults to NULL
                    "AllowedDays INT," +
                    "PRIMARY KEY (BookID)," +
                    "FOREIGN KEY (UserID) REFERENCES " + USER_TABLE_NAME + "(UserID)," +
                    "FOREIGN KEY (BookID) REFERENCES " + BOOK_TABLE_NAME + "(BookID)" +
                    ")";

    // Book Table Queries
    public static final String INSERT_BOOK =
            "INSERT INTO " + BOOK_TABLE_NAME + " (BookID, BookAuthor, BookName) VALUES (?, ?, ?)";

    public static final String DELETE_BOOK = "DELETE FROM " + BOOK_TABLE_NAME + " WHERE BookID = ?";

    public static final String SELECT_BOOK_BY_ID = "SELECT * FROM " + BOOK_TABLE_NAME + " WHERE BookID = ?";

    public static final String SELECT_USER_BY_ID = "SELECT * FROM " + USER_TABLE_NAME + " WHERE UserID = ?";

    public static final String FIND_BOOK_BY_TITLE = "SELECT * FROM " + BOOK_TABLE_NAME + " WHERE UPPER(BookName) LIKE UPPER(?)";

    public static final String UPDATE_BOOK = "UPDATE " + BOOK_TABLE_NAME + " SET BookAuthor = ?, BookName = ? WHERE BookID = ?";

    // User Table Queries
    public static final String INSERT_USER =
            "INSERT INTO " + USER_TABLE_NAME + " (UserID, UserName) VALUES (?, ?)";

    public static final String SELECT_ALL_BOOKS = "SELECT * FROM " + BOOK_TABLE_NAME;

    public static final String DELETE_USER = "DELETE FROM " + USER_TABLE_NAME + " WHERE UserID = ?";

    public static final String SELECT_ALL_USERS = "SELECT * FROM " + USER_TABLE_NAME;

    public static final String FIND_USER_BY_NAME = "SELECT * FROM " + USER_TABLE_NAME + " WHERE UPPER(UserName) LIKE UPPER(?)";

    public static final String UPDATE_USER = "UPDATE " + USER_TABLE_NAME + " SET UserName = ? WHERE UserID = ?";

    // User-Book Table Queries
    public static final String INSERT_USER_BOOK =
            "INSERT INTO " + USER_BOOK_TABLE_NAME + " (UserID, BookID, DateHired) VALUES (?, ?, ?)";

    public static final String UPDATE_USER_BOOK = "UPDATE " + USER_BOOK_TABLE_NAME + " SET DateHired = ? WHERE UserID = ? AND BookID = ?";

    public static final String DELETE_USER_BOOK = "DELETE FROM " + USER_BOOK_TABLE_NAME + " WHERE BookID = ?";

    public static final String SELECT_USER_BOOKS_BY_USER_ID =
            "SELECT " + USER_BOOK_TABLE_NAME + ".*, " + BOOK_TABLE_NAME + ".BookName, " + BOOK_TABLE_NAME + ".BookAuthor, " + USER_TABLE_NAME + ".UserName" +
            " JOIN " + BOOK_TABLE_NAME + " ON " + USER_BOOK_TABLE_NAME + ".BookID = " + BOOK_TABLE_NAME + ".BookID" +
            " JOIN " + USER_TABLE_NAME + " ON " + USER_BOOK_TABLE_NAME + ".UserID = " + USER_TABLE_NAME + ".UserID" +
            " WHERE UserID = ?";

    public static final String SELECT_USER_BOOK_BY_BOOK_ID =
            "SELECT " + USER_BOOK_TABLE_NAME + ".*, " + BOOK_TABLE_NAME + ".BookName, " + BOOK_TABLE_NAME + ".BookAuthor, " + USER_TABLE_NAME + ".UserName" +
            " FROM " + USER_BOOK_TABLE_NAME +
            " JOIN " + BOOK_TABLE_NAME + " ON " + USER_BOOK_TABLE_NAME + ".BookID = " + BOOK_TABLE_NAME + ".BookID" +
            " JOIN " + USER_TABLE_NAME + " ON " + USER_BOOK_TABLE_NAME + ".UserID = " + USER_TABLE_NAME + ".UserID" +
            " WHERE BookID = ?";

    public static final String SELECT_ALL_USER_BOOKS =
            "SELECT " + USER_BOOK_TABLE_NAME + ".*, " + BOOK_TABLE_NAME + ".BookName, " + BOOK_TABLE_NAME + ".BookAuthor, " + USER_TABLE_NAME + ".UserName" +
            " FROM " + USER_BOOK_TABLE_NAME +
            " JOIN " + BOOK_TABLE_NAME + " ON " + USER_BOOK_TABLE_NAME + ".BookID = " + BOOK_TABLE_NAME + ".BookID" +
            " JOIN " + USER_TABLE_NAME + " ON " + USER_BOOK_TABLE_NAME + ".UserID = " + USER_TABLE_NAME + ".UserID";
}
