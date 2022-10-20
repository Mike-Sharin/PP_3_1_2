package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ForAllController {

    @GetMapping()
    public String index(Model model, Principal principal){
        model.addAttribute("login", (principal == null ? "" : principal.getName()));
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error,
                        @RequestParam(name = "logout", required = false) String logout,
                        Model model){
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "loginPage";
    }
}
