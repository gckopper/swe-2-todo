package com.example.todo.infrastructure.repository;

import com.example.todo.infrastructure.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<UserDto, UUID> {
}
