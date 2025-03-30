package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    public void save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public User show(int id) {
        return userDao.show(id);
    }

    @Transactional
    public void update(int id, User updateUser) {
        updateUser.setPassword(new BCryptPasswordEncoder().encode(updateUser.getPassword()));
        userDao.update(id, updateUser);
    }

    @Transactional
    public void delete(int id) {
        userDao.delete(id);
    }
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found!", email));
        }
        return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}