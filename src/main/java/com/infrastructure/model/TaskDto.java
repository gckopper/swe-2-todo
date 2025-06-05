package com.infrastructure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
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
    private UUID ownerUserId;
    @NotNull
    private UUID assignedUserId;
    @NotNull
    private Date createAt;
}
