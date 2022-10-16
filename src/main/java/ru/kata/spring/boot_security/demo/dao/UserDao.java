package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserDao {
    List<User> findAllUser();

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(Long user);

    User findUser(Long id);

    User findUser(String login);

    List<Role> findRoles();

    void updatePass(User user);
}
