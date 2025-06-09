package com.pdc.library.db.interfaces;

import com.pdc.library.models.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UserRepository {
    void addUser(User user) throws SQLException;
    User findUserById(int id);
    Collection<User> findUserByName(String name);
    void removeUser(int userId) throws SQLException;
}
