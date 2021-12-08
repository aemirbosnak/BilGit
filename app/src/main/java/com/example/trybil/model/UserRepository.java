package com.example.trybil.model;

public class UserRepository {
    private final User user1;
    public UserRepository() {
        user1 = new User("ardaiynem@gmail.com", "pass123");
    }

    public User getUser() {
        return user1;
    }
}
