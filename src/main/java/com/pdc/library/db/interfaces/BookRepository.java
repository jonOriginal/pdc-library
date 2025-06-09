package com.pdc.library.db.interfaces;

import com.pdc.library.models.Book;

import java.sql.SQLException;
import java.util.Collection;

public interface BookRepository {
    void addBook(Book book) throws SQLException;
    void removeBook(int bookId) throws SQLException;
    void updateBook(Book book);

    Collection<Book> findAllBooks();

    Book findBookById(int id);
    Collection<Book> findBookByTitle(String title);
}
