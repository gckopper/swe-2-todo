package com.example.todo.domain.model;

import com.example.todo.domain.repository.UserRepositoryPort;
import com.example.todo.domain.service.UserService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryPort userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user, String password) {
        return userRepository.save(user, passwordEncoder.encode(password)).orElse(null);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(UUID id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    User updatedUser = existingUser.toBuilder()
                            .name(user.getName())
                            .email(user.getEmail())
                            .build();
                    return userRepository.save(updatedUser);
                }).orElse(Optional.empty());
    }

    @Override
    public boolean deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}