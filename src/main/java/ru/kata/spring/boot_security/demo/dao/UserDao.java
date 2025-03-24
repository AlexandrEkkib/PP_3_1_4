package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    Object save(User user);

    User show(int id);

    void update(int id, User updateUser);

    void delete(int id);

    User findByUsername(String username);

    User findByEmail(String email);
}