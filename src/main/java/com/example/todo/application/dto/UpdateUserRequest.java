package com.example.todo.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Informe um email válido")
    private String email;
}