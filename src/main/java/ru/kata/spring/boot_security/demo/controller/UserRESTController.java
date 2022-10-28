package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRESTController {
    private final UserService userService;

    public UserRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping("/users")
    public void editUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
