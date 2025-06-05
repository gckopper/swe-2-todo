package com.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@Setter
public class User {
    private UUID id;
    private String name;
    private String email;
}
