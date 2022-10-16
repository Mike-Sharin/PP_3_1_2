package ru.kata.spring.boot_security.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAllUser() {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    public void createUser(User user) {
        user.setPass("{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPass()));
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User updateUser) {
        entityManager.merge(updateUser);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User findUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUser(String login) {
        TypedQuery<User> query = entityManager.createQuery("FROM User WHERE login = :login", User.class).setParameter("login", login);
        return query.getSingleResult();
    }

    @Override
    public List<Role> findRoles() {
        TypedQuery<Role> query = entityManager.createQuery("FROM Role", Role.class);
        return query.getResultList();
    }

    @Override
    public void updatePass(User updateUser) {
        updateUser.setPass("{bcrypt}" + new BCryptPasswordEncoder().encode(updateUser.getPass()));
        entityManager.merge(updateUser);
    }
}
