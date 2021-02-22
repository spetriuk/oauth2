package com.petriuk.dao;

import com.petriuk.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class HibernateUserDao extends AbstractHibernateDao<User>
    implements UserDao {

    @Override
    public void create(User user) {
        createUpdate(user);
    }

    @Override
    public void update(User user) {
        createUpdate(user);
    }

    @Override
    public void remove(User user) {
        deleteEntity(user);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> typedQuery = getSession()
            .createQuery("from User", User.class);
        return typedQuery.getResultList();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        TypedQuery<User> query = getSession()
            .createQuery("from User where login = :login", User.class);
        query.setParameter("login", login);
        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = getSession()
            .createQuery("from User where email = :email", User.class);
        query.setParameter("email", email);
        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
