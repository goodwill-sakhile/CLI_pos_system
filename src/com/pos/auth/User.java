package com.pos.auth;

public class User {
    private final String username;
    private final String password; // In real life, this would be hashed

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validate(String inputPassword) {
        return password.equals(inputPassword); // Simplified, insecure
    }

    public String getUsername() {
        return username;
    }
}
