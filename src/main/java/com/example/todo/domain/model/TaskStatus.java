package com.example.todo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

public enum TaskStatus {
    BACKLOG("Backlog"),
    IN_PROGRESS("InProgress"),
    IN_REVIEW("InReview"),
    CLOSED("Closed");
    private final String value;
    TaskStatus(String value){
        this.value = value;
    }
    public String getValue(){ return this.value; }
}
