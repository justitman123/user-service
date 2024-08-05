package de.comparus.userservice.repository;

import de.comparus.userservice.model.User;

import java.util.List;

public interface UserRepository {
    List<User> fetchUsers(String username, String name, String surname);
}
