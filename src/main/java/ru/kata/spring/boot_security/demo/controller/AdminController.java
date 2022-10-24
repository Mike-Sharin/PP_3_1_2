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

//    @PostConstruct
//    private void postConstruct() {
//        User userAdmin = new User("admin_name", "adminson", (byte)  25, "admin", "admin@ya.ru");
//        User userUser = new User("user_name", "userson", (byte) 35, "user", "user@ya.ru");
//
//        Role roleAdmin = new Role("ROLE_ADMIN");
//        Role roleUser = new Role("ROLE_USER");
//
//        Set<Role> setAdmin = new HashSet<>();
//        setAdmin.add(roleAdmin);
//        setAdmin.add(roleUser);
//
//        Set<Role> setUser = new HashSet<>();
//        setUser.add(roleUser);
//
//        userAdmin.setRoles(setAdmin);
//        userUser.setRoles(setUser);
//
//        roleService.addRole(roleAdmin);
//        roleService.addRole(roleUser);
//
//        userService.addUser(userAdmin);
//        userService.addUser(userUser);
//    }

    @GetMapping()
    public String listUsers(Model model, Principal principal){
        List<User> listUsers = userService.getAll();
        List<Role> listRoles = roleRepository.findAll();

        System.out.println("_____________________________________________________________________________________________");
        System.out.println("open listUsers");
        System.out.println("_____________________________________________________________________________________________");
        System.out.println(principal);
        System.out.println("____________________________");
        System.out.println(listUsers);
        System.out.println("____________________________");
        System.out.println(listRoles);
        System.out.println("_____________________________________________________________________________________________");
        System.out.println("close listUsers");
        System.out.println("_____________________________________________________________________________________________");
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("authorizedUser", userService.getByEmail(principal.getName()));
        model.addAttribute("user", new User());


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
    public String newUser(@ModelAttribute("user") User user, Model model) {
        System.out.println("_____________________________________________________________________________________________");
        System.out.println("open newUser");
        System.out.println("_____________________________________________________________________________________________");
        System.out.println(user);
        System.out.println("_____________________________________________________________________________________________");
        System.out.println("close newUser");
        System.out.println("_____________________________________________________________________________________________");
        model.addAttribute("roles", roleRepository.findAll());
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, Model model) {
        System.out.println("_____________________________________________________________________________________________");
        System.out.println("open create");
        System.out.println("_____________________________________________________________________________________________");
        System.out.println(user);
        System.out.println("____________________________");
        System.out.println(model);
        System.out.println("_____________________________________________________________________________________________");
        System.out.println("close create");
        System.out.println("_____________________________________________________________________________________________");
        model.addAttribute("roles", roleRepository.findAll());
        if (userService.getByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("login", "error.login", "Пользователь с таким логином уже существует" );
        }
        if(bindingResult.hasErrors()) {
            return "/mainPage";
        }
        userService.addUser(user);
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
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") Long id) {
//        User user = userService.getById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleRepository.findAll());
//        return "/admin/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
//        if(bindingResult.hasErrors()) {
//            model.addAttribute("roles", roleRepository.findAll());
//            return "/admin/edit";
//        }
//        userService.editUser(user);
//        return  "redirect:/admin";
//    }
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
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        userService.delete(id);
//        return "redirect:/admin";
//    }
}
