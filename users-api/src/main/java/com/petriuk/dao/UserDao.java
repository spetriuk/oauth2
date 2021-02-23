package com.petriuk.dao;

import com.petriuk.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void create(User user);

    void update(User user);

    void remove(User user);

    List<User> findAll();

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);
}
