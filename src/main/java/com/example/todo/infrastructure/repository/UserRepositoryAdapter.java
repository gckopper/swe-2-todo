package com.example.todo.infrastructure.repository;

import com.example.todo.domain.model.User;
import com.example.todo.domain.repository.UserRepositoryPort;
import com.example.todo.infrastructure.dto.UserDto;
import com.example.todo.infrastructure.mapper.UserMapper;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final JpaUserRepository userRepository;

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId).map(UserMapper::toDomain);
    }

    @Override
    public void updateExternalCalendarToken(UUID userId, String token) {
        Optional<UserDto> userOption = userRepository.findById(userId);

        if (userOption.isPresent()) {
            UserDto user = userOption.get();
            user.setExternalCalendarServiceToken(token);
            userRepository.save(user);
        }
    }
}
