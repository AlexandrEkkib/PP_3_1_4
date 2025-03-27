package ru.kata.spring.boot_security.demo.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {
    private final UserServiceImpl userServiceImpl;

    public ProfileRestController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(userServiceImpl.findByUsername(principal.getName()));
    }
}