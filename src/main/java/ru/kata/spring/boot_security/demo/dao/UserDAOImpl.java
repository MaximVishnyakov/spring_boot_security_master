package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return em.createQuery("from User").getResultList();
    }

    @Override
    public User getUserById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public void update(long id, User updatedUser) {
        em.merge(updatedUser);
    }

    @Override
    public void delete(long id) {
        em.createQuery("delete from User user where user.id = ?1")
                .setParameter(1, id)
                .executeUpdate();
    }

    @Override
    public User getUserByUsername(String username) {
        System.out.println((User) em.createQuery("from User user where user.username = ?1")
                .setParameter(1, username)
                .getSingleResult());

        return (User) em.createQuery("from User user where user.username = ?1")
                .setParameter(1, username)
                .getSingleResult();
    }

    @Override
    public void addRoleToUser(User user, Role role) {

        em.createQuery("insert into User user = ?1 (roles) select role from Role role where role = ?2")
                .setParameter(1, user)
                .setParameter(2, role)
                .executeUpdate();
    }
}

