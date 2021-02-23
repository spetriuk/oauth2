package com.petriuk.service;

public class RoleNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Role not found for current user";
    }
}
