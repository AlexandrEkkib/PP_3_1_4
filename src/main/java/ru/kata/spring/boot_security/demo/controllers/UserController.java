package ru.kata.spring.boot_security.demo.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@RestController
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/user")
    public String userPage(Principal principal, ModelMap model) {
        User user = userServiceImpl.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/admin")
    public String print(Principal principal, ModelMap model) {
        User user = userServiceImpl.findByUsername(principal.getName());
        User userEmpty = new User();
        model.addAttribute("userEmpty", userEmpty);
        model.addAttribute("user", user);
        model.addAttribute("users", userServiceImpl.getAllUsers());
        return "admin";
    }
}