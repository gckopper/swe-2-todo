package com.example.todo.domain.repository;

import com.example.todo.domain.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepositoryPort {
    Optional<Task> findById(UUID taskId);
    List<Task> findByUserId(UUID userId);
}
