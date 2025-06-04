package com.infrastructure.repository;

import com.domain.model.Task;
import com.domain.repository.TaskRepositoryPort;
import com.infrastructure.model.TaskMapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class TaskRepositoryAdapter implements TaskRepositoryPort {
    JpaTaskRepository taskRepository;
    TaskMapper taskMapper;

    @Override
    public Optional<Task> findById(BigInteger taskId) {
        return Optional.empty();
    }

    @Override
    public List<Task> findByUserId(BigInteger userId) {
        return null;
    }
}
