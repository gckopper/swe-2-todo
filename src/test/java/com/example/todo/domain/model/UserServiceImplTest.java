package com.example.todo.domain.model;

import com.example.todo.domain.repository.UserRepositoryPort;
import com.example.todo.domain.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    UserService userService;
    @Mock
    UserRepositoryPort userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    AutoCloseable closableMocks;

    private static final String PASSWORD_HASH = "$2a$10$WD9DIe3ZO5WohHRObfn9Ru8Gvnf3mr63pxyQwvkQ9izTbg0M8AcOm";

    @BeforeEach
    void setUp() {
        closableMocks = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @AfterEach
    void tearDown() throws Exception {
        closableMocks.close();
    }

    @Test
    void createWithError() {
        User user = User.builder()
                .build();
        when(userRepository.save(user, PASSWORD_HASH)).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn(PASSWORD_HASH);
        Assertions.assertNull(userService.createUser(user, "password"));
    }
    @Test
    void createUser() {
        User user = User.builder()
                .build();
        when(userRepository.save(user, PASSWORD_HASH)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("password")).thenReturn(PASSWORD_HASH);
        Assertions.assertEquals(user, userService.createUser(user, "password"));
    }

    @Test
    void getUserByNonExistingId() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(uuid)).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), userService.getUserById(uuid));
    }
    @Test
    void getUserById() {
        UUID uuid = UUID.randomUUID();
        User user = User.builder()
                .id(uuid)
                .build();
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        Assertions.assertEquals(user, userService.getUserById(uuid).get());
    }

    @Test
    void updateNonExistingUser() {
        UUID uuid = UUID.randomUUID();
        User user = User.builder()
                .id(uuid)
                .build();
        when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        Optional<User> expected = Optional.empty();
        Assertions.assertEquals(expected, userService.updateUser(uuid, user));
    }
    @Test
    void updateUser() {
        UUID uuid = UUID.randomUUID();
        User user = User.builder()
                .id(uuid)
                .build();
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(Optional.of(user));

        Optional<User> expected = Optional.of(user);
        Assertions.assertEquals(expected, userService.updateUser(uuid, user));
    }

    @Test
    void deleteUser() {
    }
}