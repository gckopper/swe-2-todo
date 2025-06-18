package com.example.todo.domain.model;

import com.example.todo.domain.service.UserService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
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
                });
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