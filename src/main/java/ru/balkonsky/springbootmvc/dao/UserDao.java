package ru.balkonsky.springbootmvc.dao;

import ru.balkonsky.springbootmvc.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User showUserById(int id);

    void saveUser(User user);

    void updateUser(User updatedUser, int id);

    void deleteUser(int id);

    User getUserByUsername(String username);
}
