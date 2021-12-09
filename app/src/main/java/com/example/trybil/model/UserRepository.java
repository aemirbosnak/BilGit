package com.example.trybil.model;

public class UserRepository {
    private final String email;
    private final String password;

    public UserRepository(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getUserEmail() {
        return email;
    }

    public String getUserPassword() {
        return password;
    }
}
