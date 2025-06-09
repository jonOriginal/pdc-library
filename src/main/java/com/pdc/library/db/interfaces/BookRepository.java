package com.pdc.library.db.interfaces;

import com.pdc.library.models.Book;

import java.util.Collection;

public interface BookRepository {
    void addBook(Book book);
    void removeBook(Book book);
    void updateBook(Book book);

    Book findBookById(int id);
    Collection<Book> findBookByTitle(String title);
}
