package ru.balkonsky.springbootmvc.service;

import org.springframework.stereotype.Component;
import ru.balkonsky.springbootmvc.model.User;

import java.util.List;

@Component
public interface UserService {

    List<User> getAllUsers();

    User showUserById(int id);

    void saveUser(User user);

    void updateUser(User updatedUser, int id);

    void deleteUser(int id);

    User getUserByUsername(String username);

}
