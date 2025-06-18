package com.example.todo.infrastructure.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.todo.domain.model.TaskStatus;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private UserDto ownerUser;
    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private UserDto assignedUser;
    private OffsetDateTime expectedCompletionDate;
    private String externalCalendarEventId;
}
