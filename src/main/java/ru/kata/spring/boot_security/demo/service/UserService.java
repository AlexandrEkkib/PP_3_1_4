package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void save(User user);

    User show(int id);

    void update(int id, User updateUser);

    void delete(int id);

}