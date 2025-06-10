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
                    "PRIMARY KEY (UserID, BookID)," +
                    "FOREIGN KEY (UserID) REFERENCES " + USER_TABLE_NAME + "(UserID)," +
                    "FOREIGN KEY (BookID) REFERENCES " + BOOK_TABLE_NAME + "(BookID)" +
                    ")";


    public static final String INSERT_BOOK =
            "INSERT INTO " + BOOK_TABLE_NAME + " (BookID, BookAuthor, BookName) VALUES (?, ?, ?)";

    public static final String INSERT_USER =
            "INSERT INTO " + USER_TABLE_NAME + " (UserID, UserName) VALUES (?, ?)";

    public static final String INSERT_USER_BOOK =
            "INSERT INTO " + USER_BOOK_TABLE_NAME + " (UserID, BookID, DateHired) VALUES (?, ?, ?)";

    public static final String SELECT_BOOK_BY_ID = "SELECT * FROM " + BOOK_TABLE_NAME + " WHERE BookID = ?";

    public static final String SELECT_USER_BY_ID = "SELECT * FROM " + USER_TABLE_NAME + " WHERE UserID = ?";

    public static final String SELECT_USER_BOOK_BY_ID = "SELECT * FROM " + USER_BOOK_TABLE_NAME + " WHERE UserID = ? AND BookID = ?";

    public static final String FIND_BOOK_BY_TITLE = "SELECT * FROM " + BOOK_TABLE_NAME + " WHERE UPPER(BookName) LIKE UPPER(?)";

    public static final String UPDATE_BOOK = "";

    public static final String UPDATE_USER = "";

    public static final String UPDATE_USER_BOOK = "";

    public static final String DELETE_BOOK = "DELETE FROM " + BOOK_TABLE_NAME + " WHERE BookID = ?";

    public static final String DELETE_USER = "";

    public static final String DELETE_USER_BOOK = "";

    public static final String SELECT_ALL_BOOKS = "";

    public static final String SELECT_ALL_USERS = "";

    public static final String SELECT_ALL_USER_BOOKS = "";

    public static final String SELECT_BOOKS_BY_USER_ID = "";

    public static final String SELECT_OVERDUE_BOOKS = "";
}
