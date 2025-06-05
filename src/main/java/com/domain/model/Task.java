package com.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@Setter
public class Task {
    private UUID id;
    private User owner;
    private User assignedToUser;
    private String title;
    private String description;
    private String status;
}
