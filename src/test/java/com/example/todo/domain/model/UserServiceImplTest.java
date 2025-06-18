package com.example.todo.domain.model;

import com.example.todo.domain.repository.UserRepositoryPort;
import com.example.todo.domain.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    UserService userService;
    @Mock
    UserRepositoryPort userRepository;
    AutoCloseable closableMocks;

    @BeforeEach
    void setUp() {
        closableMocks = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closableMocks.close();
    }

    @Test
    void createWithError() {
        User user = User.builder()
                .build();
        when(userRepository.save(user)).thenReturn(Optional.empty());
        Assertions.assertNull(userService.createUser(user));
    }
    @Test
    void createUser() {
        User user = User.builder()
                .build();
        when(userRepository.save(user)).thenReturn(Optional.of(user));
        Assertions.assertEquals(user, userService.createUser(user));
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