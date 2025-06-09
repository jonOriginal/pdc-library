package com.pdc.library.db.interfaces;

import com.pdc.library.models.User;

import java.util.Collection;

public interface UserRepository {
    void addUser(User user);
    User findUserById(int id);
    Collection<User> findUserByName(String name);
}
