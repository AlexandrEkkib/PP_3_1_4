package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getAllUsers();

    Object save(User user);

    User show(int id);

    void update(int id, User updateUser);

    void delete(int id);

    User findByUsername(String username);

    User findByEmail(String email);
}