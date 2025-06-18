package com.example.todo.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "google.calendar")
public class GoogleCalendarConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
} 