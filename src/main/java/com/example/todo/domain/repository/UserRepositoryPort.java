package com.example.todo.domain.repository;

import com.example.todo.domain.model.User;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    @NotNull
    Optional<User> findById(@NotNull UUID userId);
    @NotNull
    Optional<User> save(@NotNull User user);
    @NotNull
    void deleteById(@NotNull UUID userId);
    @NotNull
    boolean existsById(@NotNull UUID userId);
    void updateExternalCalendarToken(@NotNull UUID userId, @NotNull String token);

    @NotNull
    Optional<User> findByUsername(@NotNull String username);
}
