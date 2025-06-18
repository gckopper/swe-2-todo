package com.example.todo.domain.service;

import com.example.todo.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(User user, String password);
    Optional<User> getUserById(UUID id);
    Optional<User> updateUser(UUID id, User user);
    boolean deleteUser(UUID id);
}