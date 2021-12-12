package com.example.trybil.model;

public class User {
    private String email;
    private String username;
    private String department;

    public User(String email, String username, String department) {
        this.email = email;
        this.username = username;
        this.department = department;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
