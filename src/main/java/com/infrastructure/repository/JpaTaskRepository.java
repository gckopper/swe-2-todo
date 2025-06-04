package com.infrastructure.repository;

import com.domain.model.Task;
import com.infrastructure.model.TaskDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskDto, BigInteger> {
    @Override
    Optional<TaskDto> findById(BigInteger id);
    List<Task> findByUserId(BigInteger userId);
}
