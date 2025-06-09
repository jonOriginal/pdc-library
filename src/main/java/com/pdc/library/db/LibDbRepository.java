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
    }

    public void createBookTable() {
        // Implementation for creating the book table in the database
    }

    public void createUserTable() {
        // Implementation for creating the user table in the database
    }

    @Override
    public void addUserBook(UserBook userBook) {

    }

    @Override
    public void removeUserBook(UserBook userBook) {

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

    }

    @Override
    public void removeBook(Book book) {

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
