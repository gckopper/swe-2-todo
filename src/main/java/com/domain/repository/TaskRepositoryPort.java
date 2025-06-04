package com.domain.repository;

import com.domain.model.Task;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface TaskRepositoryPort {
    Optional<Task> findById(BigInteger taskId);
    List<Task> findByUserId(BigInteger userId);
}
