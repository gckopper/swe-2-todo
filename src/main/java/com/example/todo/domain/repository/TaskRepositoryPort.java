package com.example.todo.domain.repository;

import com.example.todo.domain.model.Task;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepositoryPort {
    @NotNull
    Optional<Task> findById(@NotNull UUID ownerUserId);
    @NotNull
    List<Task> findByOwnerUserId(@NotNull UUID ownerUserId);
    @NotNull
    List<Task> findByAssignedUserId(@NotNull UUID assignedUserId);
}
