package com.petriuk.service;

public class UserExistException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User already exists";
    }
}
