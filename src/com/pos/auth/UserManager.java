package com.pos.auth;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final Map<String, User> users = new HashMap<>();

    public UserManager() {
        seedUsers();
    }

    private void seedUsers() {
        register("admin", "admin123");
        register("cashier", "1234");
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) return false;
        users.put(username, new User(username, password));
        return true;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.validate(password)) {
            return user;
        }
        return null;
    }
}
