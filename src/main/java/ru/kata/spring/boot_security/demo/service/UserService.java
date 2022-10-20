package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService extends UserDetailsService {

    void addUser(User user);
    void delete(long id);
    User getByEmail(String login);
    void editUser(User user);
    List<User> getAll();
    User getById(Long id);
}
