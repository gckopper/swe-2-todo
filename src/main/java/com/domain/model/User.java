package com.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
public class User {
    private String id;
    private String name;
    private String email;
}
