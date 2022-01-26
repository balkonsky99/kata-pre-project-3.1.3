package ru.balkonsky.springbootmvc.conrtoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> showAllUsers () {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> showUser (@PathVariable int id) {

        return new ResponseEntity<>(userService.showUserById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser (@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping ("{id}")
    public ResponseEntity<User> editUser (@PathVariable int id, @RequestBody User updatedUser) {
        userService.updateUser(updatedUser, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void deleteUser (@PathVariable int id) {
        userService.deleteUser(id);
    }
}
