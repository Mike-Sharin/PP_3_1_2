package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AuthorizedUserController {
    @GetMapping("/admin")
    public String adminPage(){
        return "mainPage";
    }

    @GetMapping("/user")
    public String userPage(){
        return "mainPage";
    }
}
