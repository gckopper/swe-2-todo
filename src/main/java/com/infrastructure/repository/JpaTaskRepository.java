package com.infrastructure.repository;

import com.domain.model.Task;
import com.infrastructure.model.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskDto, UUID> {
    Optional<TaskDto> findById(UUID id);
    List<Task> findByUserId(UUID userId);
}
