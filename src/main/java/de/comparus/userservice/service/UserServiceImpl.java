package de.comparus.userservice.service;

import de.comparus.userservice.model.User;
import de.comparus.userservice.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepositoryImpl;

    public UserServiceImpl(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Override
    public List<User> getAllUsers(String username, String name, String surname) {
        return userRepositoryImpl.fetchUsers(username, name, surname);
    }
}
