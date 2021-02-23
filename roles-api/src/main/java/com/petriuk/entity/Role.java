package com.petriuk.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "app_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ElementCollection
    @CollectionTable(name = "user_roles",
        joinColumns=@JoinColumn(name="role_id"))
        @Column(name="user_id")
    private List<Long> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getUserIdList() {
        return users;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.users = userIdList;
    }
}