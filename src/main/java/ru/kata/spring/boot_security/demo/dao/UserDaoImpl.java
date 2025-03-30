package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.*;

import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    @Transactional
    public Object save(User user) {
        entityManager.persist(user);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public User show(int id) {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(int id, User updateUser) {
        String[] roleUser = new String[]{"ROLE_USER"};

        User user = show(id);
        user.setUsername(updateUser.getUsername());
        user.setSurname(updateUser.getSurname());
        user.setAge(updateUser.getAge());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        if (updateUser.getRoles() == null) {
            updateUser.setRoles(roleUser);
        }
        user.setRole(updateUser.getRoles());
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        User user = show(id);
        entityManager.remove(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return entityManager.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return entityManager.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email).getSingleResult();
    }
}