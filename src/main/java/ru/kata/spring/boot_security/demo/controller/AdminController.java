package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository, RoleService roleService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
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

    @GetMapping()
    public String listUsers(Model model, Principal principal){
        model.addAttribute("listUsers", userService.getAll());
        model.addAttribute("listRoles", roleRepository.findAll());
        model.addAttribute("authorizedUser", userService.getByEmail(principal.getName()));
        model.addAttribute("newUser", new User());

        return "mainPage";
    }

//    @PatchMapping("/{id}")
//    public void update(@ModelAttribute("userEdit") @Valid User user, BindingResult bindingResult, Model model) {
//        if(bindingResult.hasErrors()) {
//            model.addAttribute("roles", roleRepository.findAll());
//         //   return "/admin/edit";
//        }
//        userService.editUser(user);
//      //  return  "redirect:/admin";
//    }

//    @GetMapping("/{id}")
//    public void show(@PathVariable("id") Long id, Model model, Principal principal) {
//        User user = userService.getById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("disabledButtonDelete", (user.getEmail().equals(principal.getName())));
//        //return "/admin/show";
//    }

//    @GetMapping()
//    public String listUsers(Model model, Principal principal){
//        model.addAttribute("users", userService.getAll());
//        model.addAttribute("authorization", principal.getName());
//        return "/admin/list";
//    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("newUser") User user, Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("newUser") @Valid User newUser,
                         BindingResult bindingResult, Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        userService.addUser(newUser);
        return "redirect:/admin";
    }
//
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") Long id, Model model, Principal principal) {
//        User user = userService.getById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("disabledButtonDelete", (user.getEmail().equals(principal.getName())));
//        return "/admin/show";
//    }
//
//    @GetMapping("/{id}")
//    public String edit(Model model, @PathVariable("id") Long id) {
//        User userEdit = userService.getById(id);
//        model.addAttribute("userEdit", userEdit);
//        model.addAttribute("roles", roleRepository.findAll());
//        System.out.println("_____________________________________________________________________________________________");
//        System.out.println("edit_____________________________________________________________________________________________");
//        System.out.println("_____________________________________________________________________________________________");
//        System.out.println(userEdit);
//        System.out.println("_____________________________________________________________________________________________");
//        System.out.println("edit_____________________________________________________________________________________________");
//        System.out.println("_____________________________________________________________________________________________");
//        return "/adminPage";
//    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        System.out.println("_____________________________________________________________________________________________");
        System.out.println("_____________________________________________________________________________________________");
        System.out.println(user);
        System.out.println("_____________________________________________________________________________________________");
        System.out.println("_____________________________________________________________________________________________");

  //      userService.editUser(user);
        return  "redirect:/admin";
    }
//
//    @PatchMapping("/{id}/pass")
//    public String updatePass(@ModelAttribute("user") User user) {
//        userService.addUser(user);
//        return  "redirect:/admin";
//    }
//
//    @GetMapping("/{id}/pass")
//    public String editPass(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.getById(id));
//        model.addAttribute("roles", roleRepository.findAll());
//        return  "/admin/pass";
//    }
//
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
