package com.example.todo.application.mapper;

import com.example.todo.application.dto.CreateTaskRequest;
import com.example.todo.application.dto.TaskResponse;
import com.example.todo.application.dto.UpdateTaskRequest;
import com.example.todo.domain.model.Task;
import com.example.todo.domain.model.TaskStatus;
import com.example.todo.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskApiMapperTest {

    @Test
    void toDomain() {
        CreateTaskRequest request = CreateTaskRequest.builder()
                .title("Title")
                .description("Desc")
                .status(TaskStatus.IN_PROGRESS)
                .ownerId(UUID.randomUUID())
                .assignedUserId(UUID.randomUUID())
                .expectedCompletionDate(OffsetDateTime.now())
                .build();

        Task expected = Task.builder()
                .title("Title")
                .description("Desc")
                .status(TaskStatus.IN_PROGRESS)
                .owner(User.builder().id(request.getOwnerId()).build())
                .assignedToUser(User.builder().id(request.getAssignedUserId()).build())
                .expectedCompletionDate(request.getExpectedCompletionDate())
                .build();
        Task actual = TaskApiMapper.toDomain(request);

        expected.setId(actual.getId());
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void toDomainWithId() {
        UUID id = UUID.randomUUID();
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .title("Title")
                .description("Desc")
                .status(TaskStatus.IN_PROGRESS)
                .assignedUserId(UUID.randomUUID())
                .expectedCompletionDate(OffsetDateTime.now())
                .build();

        Task expected = Task.builder()
                .id(id)
                .title("Title")
                .description("Desc")
                .status(TaskStatus.IN_PROGRESS)
                .assignedToUser(User.builder().id(request.getAssignedUserId()).build())
                .expectedCompletionDate(request.getExpectedCompletionDate())
                .build();
        Task actual = TaskApiMapper.toDomain(id, request);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void toResponse() {
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .title("Title")
                .description("Desc")
                .status(TaskStatus.IN_PROGRESS)
                .owner(User.builder().id(UUID.randomUUID()).build())
                .assignedToUser(User.builder().id(UUID.randomUUID()).build())
                .expectedCompletionDate(OffsetDateTime.now())
                .externalCalendarEventId(null)
                .build();
        TaskResponse expected = TaskResponse.builder()
                .id(task.getId())
                .title("Title")
                .description("Desc")
                .status(TaskStatus.IN_PROGRESS)
                .expectedCompletionDate(task.getExpectedCompletionDate())
                .externalCalendarEventId(task.getExternalCalendarEventId())
                .ownerUserId(task.getOwner().getId())
                .assignedToUserId(task.getAssignedToUser().getId())
                .build();
        TaskResponse actual = TaskApiMapper.toResponse(task);
        Assertions.assertEquals(expected, actual);
    }
}