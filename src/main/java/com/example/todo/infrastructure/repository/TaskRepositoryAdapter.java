package com.example.todo.infrastructure.repository;

import com.example.todo.domain.model.Task;
import com.example.todo.domain.repository.TaskRepositoryPort;
import com.example.todo.infrastructure.model.TaskMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskRepositoryAdapter implements TaskRepositoryPort {
    JpaTaskRepository taskRepository;
    TaskMapper taskMapper;

    @Override
    public Optional<Task> findById(UUID taskId) {
        return Optional.empty();
    }

    @Override
    public List<Task> findByUserId(UUID userId) {
        return null;
    }
}
