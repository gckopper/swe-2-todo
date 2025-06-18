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
        Optional<UserDto> userOption = userRepository.findById(userId);

        if (userOption.isPresent()) {
            UserDto user = userOption.get();
            return Optional.of(UserMapper.toDomain(user));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> save(User user) {
        UserDto userDto = UserMapper.toDto(user);
        UserDto savedUser = userRepository.save(userDto);
        return Optional.of(UserMapper.toDomain(savedUser));
    }

    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean existsById(UUID userId) {
        return userRepository.existsById(userId);
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
