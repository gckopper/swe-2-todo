package com.example.todo.infrastructure.repository;

import com.example.todo.domain.model.User;
import com.example.todo.domain.repository.UserRepositoryPort;
import com.example.todo.infrastructure.model.UserMapper;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryAdapter implements UserRepositoryPort {
    JpaUserRepository userRepository;

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId).map(UserMapper::toDomain);
    }
}
