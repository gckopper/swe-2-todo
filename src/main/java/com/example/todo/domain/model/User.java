package com.example.todo.domain.model;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode
public class User {
    private UUID id;
    private String name;
    private String email;
    private String externalCalendarServiceToken;

    public boolean usesExternalCalendarService() {
        return externalCalendarServiceToken != null;
    }

    // TODO adicionar a senha do usuário
}
