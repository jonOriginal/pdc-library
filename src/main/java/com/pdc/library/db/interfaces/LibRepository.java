package com.pdc.library.db.interfaces;

import com.pdc.library.models.UserBook;

import java.sql.SQLException;
import java.util.Collection;

public interface LibRepository extends BookRepository, UserRepository {
    void addUserBook(UserBook userBook) throws SQLException;

    void removeUserBook(int bookId) throws SQLException;

    void updateUserBook(UserBook userBook) throws SQLException;

    Collection<UserBook> findUserBooksByUserId(int userId) throws SQLException;

    UserBook findUserBookByBookId(int bookId) throws SQLException;

    Collection<UserBook> findOverdueBooks() throws SQLException;

    Collection<UserBook> findAllUserBooks() throws SQLException;
}
