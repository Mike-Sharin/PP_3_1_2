package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Controller
public class InsertedDefaultUsersController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InsertedDefaultUsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        Role roleAdmin = roleService.getByName("ROLE_ADMIN");
        Role roleUser = roleService.getByName("ROLE_USER");

        if(roleAdmin == null) {
            roleAdmin = new Role("ROLE_ADMIN");
            roleService.addRole(roleAdmin);
        }

        if(roleUser == null) {
            roleUser = new Role("ROLE_USER");
            roleService.addRole(roleUser);
        }

        if(userService.getByEmail("admin@ya.ru") == null) {
            User userAdmin = new User("admin_name", "adminson", (byte) 25, "admin", "admin@ya.ru");

            Set<Role> setAdmin = new HashSet<>();
            setAdmin.add(roleAdmin);
            setAdmin.add(roleUser);

            userAdmin.setRoles(setAdmin);
            userService.addUser(userAdmin);
        }

        if(userService.getByEmail("user@ya.ru") == null) {
            User userUser = new User("user_name", "userson",  (byte) 35, "user", "user@ya.ru");

            Set<Role> setUser = new HashSet<>();
            setUser.add(roleUser);

            userUser.setRoles(setUser);
            userService.addUser(userUser);
        }
    }
}
