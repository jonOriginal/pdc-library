package com.pdc.library.db;

public class LibSql {
    private LibSql() {}

    public static final String BOOK_TABLE_NAME = "Books";
    public static final String USER_TABLE_NAME = "Users";
    public static final String USER_BOOK_TABLE_NAME = "UserBooks";

    public static final String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE_NAME + " (" +
                                                   "BookID int NOT NULL," +
                                                   "BookAuthor var(255)," +
                                                   "BookName var(255)," +
                                                   "PRIMARY KEY (BookID)" +
                                                   ");";

    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " (" +
                                                   "UserID int NOT NULL," +
                                                   "UserName VAR(255)," +
                                                   "PRIMARY KEY (BookID)" +
                                                   ")";

    public static final String CREATE_USER_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_BOOK_TABLE_NAME + " (" +
                                                        "UserID int," +
                                                        "BookID int," +
                                                        "DateHired date," +
                                                        "PRIMARY KEY (UserID, BookID)," +
                                                        "FOREIGN KEY (UserID) REFERENCES " + USER_TABLE_NAME + "(UserID)," +
                                                        "FOREIGN KEY (BookID) REFERENCES " + BOOK_TABLE_NAME + "(BookID)" +
                                                        ");";

    public static final String INSERT_BOOK = "";

    public static final String INSERT_USER = "";

    public static final String INSERT_USER_BOOK = "";

    public static final String SELECT_BOOK_BY_ID = "";

    public static final String SELECT_USER_BY_ID = "";

    public static final String SELECT_USER_BOOK_BY_ID = "";

    public static final String UPDATE_BOOK = "";

    public static final String UPDATE_USER = "";

    public static final String UPDATE_USER_BOOK = "";

    public static final String DELETE_BOOK = "";

    public static final String DELETE_USER = "";

    public static final String DELETE_USER_BOOK = "";

    public static final String SELECT_ALL_BOOKS = "";

    public static final String SELECT_ALL_USERS = "";

    public static final String SELECT_ALL_USER_BOOKS = "";

    public static final String SELECT_BOOKS_BY_USER_ID = "";

    public static final String SELECT_OVERDUE_BOOKS = "";
}
