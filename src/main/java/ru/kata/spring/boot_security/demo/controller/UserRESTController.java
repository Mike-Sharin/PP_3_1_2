package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRESTController {
    private final UserService userService;

    public UserRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        System.out.println("_________________________________________________________________________________________");
        System.out.println("UserRESTController-getAll");
        System.out.println("_________________________________________________________________________________________");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        System.out.println("_________________________________________________________________________________________");
        System.out.println("UserRESTController-getById " + id);
        System.out.println("_________________________________________________________________________________________");
        return userService.getById(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        System.out.println("_________________________________________________________________________________________");
        System.out.println("UserRESTController-addUser " + user);
        System.out.println("_________________________________________________________________________________________");
        userService.addUser(user);
    }

    @PutMapping
    public void editUser(@RequestBody User user) {
        System.out.println("_________________________________________________________________________________________");
        System.out.println("UserRESTController-editUser " + user);
        System.out.println("_________________________________________________________________________________________");
        userService.addUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        System.out.println("_________________________________________________________________________________________");
        System.out.println("UserRESTController-deleteUser " + id);
        System.out.println("_________________________________________________________________________________________");
        userService.delete(id);
    }
}
