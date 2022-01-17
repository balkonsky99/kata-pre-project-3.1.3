package ru.balkonsky.springbootmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.balkonsky.springbootmvc.dao.UserDao;
import ru.balkonsky.springbootmvc.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public User showUserById(int id) {
        return userDao.showUserById(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User updatedUser, int id) {
        userDao.updateUser(updatedUser, id);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}