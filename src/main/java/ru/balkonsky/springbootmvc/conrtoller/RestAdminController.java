package ru.balkonsky.springbootmvc.conrtoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.balkonsky.springbootmvc.model.User;
import ru.balkonsky.springbootmvc.service.RoleService;
import ru.balkonsky.springbootmvc.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class RestAdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/test")
    public String test() {
        return "hello world!";
    }

    @GetMapping("")
    public List<User> showAllUsers () {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User showUser (@PathVariable int id) {
        return userService.showUserById(id);
    }

    @PostMapping("/users")
    public User createUser (@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PatchMapping ("/users/{id}")
    public User editUser (@PathVariable int id, @RequestBody User updatedUser) {
        userService.updateUser(updatedUser, id);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser (@PathVariable int id) {
        userService.deleteUser(id);
    }
}
