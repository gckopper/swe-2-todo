package com.example.todo.application.mapper;

import com.example.todo.application.dto.CreateUserRequest;
import com.example.todo.application.dto.UpdateUserRequest;
import com.example.todo.application.dto.UserResponse;
import com.example.todo.domain.model.User;

import java.util.UUID;

public class UserApiMapper {

    public static User toDomain(CreateUserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    public static User toDomain(UUID id, UpdateUserRequest request) {
        return User.builder()
                .id(id)
                .name(request.getName())
                .email(request.getEmail())
                .build();
    }

    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}