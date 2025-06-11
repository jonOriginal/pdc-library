package com.pdc.library.db.interfaces;

import com.pdc.library.models.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UserRepository {
    void addUser(User user) throws SQLException;

    User findUserById(int id) throws SQLException;

    Collection<User> findUserByName(String name) throws SQLException;

    void removeUser(int userId) throws SQLException;

    Collection<User> findAllUsers() throws SQLException;

    void updateUser(User user) throws SQLException;
}
