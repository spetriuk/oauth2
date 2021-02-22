package com.petriuk.dao;

import com.petriuk.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    List<Role> findAll();

    Optional<Role> findByName(String name);
}
