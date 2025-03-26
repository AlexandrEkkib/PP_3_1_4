package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserServiceImpl userServiceImpl;

    public UserRestController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        List<User> allUsers = userServiceImpl.getAllUsers();
        return allUsers;
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable int id) {
        User user = userServiceImpl.show(id);
        return user;
    }

    @PostMapping("users")
    @PreAuthorize("hasRole('ADMIN')")
    public User addNewUser(@RequestBody @Valid User user) {
        userServiceImpl.save(user);
        return user;
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userServiceImpl.update(user.getId(), user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        userServiceImpl.delete(user.getId());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getUserPage(Principal pr) {
        return new ResponseEntity(userServiceImpl.findByUsername(pr.getName()), HttpStatus.OK);
    }
}
