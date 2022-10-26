package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AuthorizedUserController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthorizedUserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String listUsers(Model model, Principal principal){
        model.addAttribute("listUsers", userService.getAll());
        model.addAttribute("listRoles", roleRepository.findAll());
        model.addAttribute("authorizedUser", userService.getByEmail(principal.getName()));
        model.addAttribute("newUser", new User());

        return "mainPage";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User newUser, Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        userService.addUser(newUser);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("newUser") User user) {
        userService.addUser(user);
        return  "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
