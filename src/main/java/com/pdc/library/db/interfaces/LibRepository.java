package com.pdc.library.db.interfaces;

import com.pdc.library.models.UserBook;

import java.util.Collection;

public interface LibRepository extends BookRepository, UserRepository {
    void addUserBook(UserBook userBook);
    void removeUserBook(UserBook userBook);

    Collection<UserBook> findUserBooksByUserId(int userId);
    UserBook findUserBookByBookId(int bookId);

    Collection<UserBook> findOverdueBooks();
    Collection<UserBook> findOverdueBooksByUserId(int userId);
}
