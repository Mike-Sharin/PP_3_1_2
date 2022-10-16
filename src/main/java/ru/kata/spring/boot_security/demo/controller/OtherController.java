package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class OtherController {

    @GetMapping()
    public String index(Model model, Principal principal){
        model.addAttribute("login", (principal == null ? "" : principal.getName()));
        return "index";
    }
}
