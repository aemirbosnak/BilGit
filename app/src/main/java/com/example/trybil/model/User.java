package com.example.trybil.model;

public class User {
    private String email;
    private String username;
    private String department;
    private boolean priv;

    public User(String email, String username, String department, boolean priv) {
        this.email = email;
        this.username = username;
        this.department = department;
        this.priv = priv;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getDepartment() {
        return department;
    }

    public boolean getPriv() {
        return priv;
    }

}
