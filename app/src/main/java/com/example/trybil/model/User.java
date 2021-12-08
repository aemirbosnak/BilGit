package com.example.trybil.model;

public class User {
    private final String email;
    private final String password;

    public User(String email, String password) {
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
