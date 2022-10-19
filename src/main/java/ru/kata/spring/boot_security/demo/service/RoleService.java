package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService extends UserDetailsService {

    void addRole(Role role);
}
