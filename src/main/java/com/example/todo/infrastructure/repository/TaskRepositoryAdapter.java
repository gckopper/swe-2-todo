package com.example.todo.infrastructure.repository;

import com.example.todo.domain.model.Task;
import com.example.todo.domain.repository.TaskRepositoryPort;
import com.example.todo.infrastructure.model.TaskMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskRepositoryAdapter implements TaskRepositoryPort {
    private final JpaTaskRepository taskRepository;

    @Override
    public Optional<Task> findById(UUID taskId) {
        return taskRepository.findById(taskId).map(TaskMapper::toDomain);
    }

    @Override
    public List<Task> findByOwnerUserId(UUID ownerUserId) {
        return taskRepository.findByOwnerUserId(ownerUserId).stream().map(TaskMapper::toDomain).toList();
    }

    @Override
    public List<Task> findByAssignedUserId(UUID assignedUserId) {
        return taskRepository.findByAssignedUserId(assignedUserId).stream().map(TaskMapper::toDomain).toList();
    }
}
