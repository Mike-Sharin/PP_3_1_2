package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleRESTController {
    private final RoleRepository roleService;

    public RoleRESTController(RoleRepository roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public List<Role> getAll() {
        return roleService.findAll();
    }

    @GetMapping("/roles/{id}")
    public Role getById(@PathVariable Long id) {
        return roleService.getById(id);
    }
}
