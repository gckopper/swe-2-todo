package com.example.todo.infrastructure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @Id
    private UUID id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String status;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private UserDto ownerUser;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private UserDto assignedUser;
    @NotNull
    private Date createAt;
    private OffsetDateTime expectedCompletionDate;
    private String externalCalendarEventId;
}
