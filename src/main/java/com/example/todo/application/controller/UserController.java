package com.example.todo.application.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.application.dto.CreateUserRequest;
import com.example.todo.application.dto.UpdateUserRequest;
import com.example.todo.application.dto.UserResponse;
import com.example.todo.application.mapper.UserApiMapper;
import com.example.todo.domain.model.User;
import com.example.todo.domain.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        User user = UserApiMapper.toDomain(request);
        User createdUser = userService.createUser(user);
        UserResponse response = UserApiMapper.toResponse(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(UserApiMapper.toResponse(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, UserApiMapper.toDomain(id, request))
                .map(user -> ResponseEntity.ok(UserApiMapper.toResponse(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}