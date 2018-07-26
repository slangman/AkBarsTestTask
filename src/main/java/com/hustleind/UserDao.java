package com.hustleind;

import java.util.List;

public interface UserDao {
    User getUserById(int id);

    User getUserByEmail(String email);

    List<User> getUsers();

    void addUser(User user);

    void updateUser(User user);

    void deactivateUser(User user);

    void deleteUserById(int id);
}
