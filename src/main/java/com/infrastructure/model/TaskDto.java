package com.infrastructure.model;

import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigInteger;

@Entity
@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @Id
    private BigInteger id;
    private String title;
}
