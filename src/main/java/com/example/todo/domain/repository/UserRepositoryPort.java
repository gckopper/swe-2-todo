package com.example.todo.domain.repository;

import com.example.todo.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    Optional<User> findById(UUID userId);
    void updateExternalCalendarToken(UUID userId, String token);
}
