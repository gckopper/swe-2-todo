package com.example.todo.infrastructure.repository;

import com.example.todo.infrastructure.model.TaskDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskDto, UUID> {
    @NotNull
    List<TaskDto> findByOwnerUser_Id(@NotNull UUID ownerUserId);
    @NotNull
    List<TaskDto> findByAssignedUser_Id(@NotNull UUID assignedUserId);
}
