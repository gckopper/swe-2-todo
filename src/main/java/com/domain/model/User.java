package com.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Builder(toBuilder = true)
@Getter
@Setter
public class User {
    private BigInteger id;
    private String name;
    private String email;
}
