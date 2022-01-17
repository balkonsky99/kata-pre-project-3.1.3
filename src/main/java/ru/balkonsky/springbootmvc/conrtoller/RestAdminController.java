package ru.balkonsky.springbootmvc.conrtoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.balkonsky.springbootmvc.model.User;
import ru.balkonsky.springbootmvc.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users/")
public class RestAdminController {

    private final UserService userService;

    @Autowired
    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> showAllUsers () {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public User showUser (@PathVariable int id) {
        return userService.showUserById(id);
    }

    @PostMapping("")
    public User createUser (@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PatchMapping ("{id}")
    public User editUser (@PathVariable int id, @RequestBody User updatedUser) {
        userService.updateUser(updatedUser, id);
        return updatedUser;
    }

    @DeleteMapping("{id}")
    public void deleteUser (@PathVariable int id) {
        userService.deleteUser(id);
    }
}
