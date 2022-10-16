package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void addUser(User user);

    void editUser(User user);

    void editPass(User user);

    void removeUser(Long id);

    User findUser(Long id);

    User findUser(String login);

    List<Role>  findRoles();
}
