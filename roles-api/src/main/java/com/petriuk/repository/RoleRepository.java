package com.petriuk.repository;

import com.petriuk.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query("SELECT DISTINCT name FROM Role")
    List<String> selectDistinctNames();

    @Query("SELECT role.name FROM Role role JOIN role.users WHERE user_id = :userId")
    Optional<String> findRoleNameByUserId(@Param("userId") Long userId);

    Optional<Role> findByName(String name);

    @Modifying
    @Query(value = "DELETE FROM user_roles WHERE user_id = ?1", nativeQuery = true)
    void deleteUserId(Long userId);

}
