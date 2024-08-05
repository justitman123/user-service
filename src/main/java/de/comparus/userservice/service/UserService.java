package de.comparus.userservice.service;

import de.comparus.userservice.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(String username, String name, String surname);
}
