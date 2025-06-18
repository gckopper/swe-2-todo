package com.example.todo.application.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
}