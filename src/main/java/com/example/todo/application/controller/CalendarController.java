package com.example.todo.application.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.domain.repository.UserRepositoryPort;
import com.example.todo.infrastructure.calendar.oauth.GoogleOAuthProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final GoogleOAuthProvider oauthProvider;
    private final UserRepositoryPort userRepository;

    @Operation(
        summary = "Redirecionamento do usuário para iniciar o fluxo authorization code OAuth2 da autenticação do Google",
        description = "O usuário deve deve abrir este endpoint em seu browser para iniciar o fluxo de autenticação."
    )
    @ApiResponse(responseCode = "302")
    @GetMapping("/oauth2/googlecalendar/redirect")
    public ResponseEntity<Void> redirectGoogleCalendar() {
        URI location = oauthProvider.getAuthorizationCodeUrl();
        
        return ResponseEntity
                .status(HttpStatus.FOUND) // 302            
                .location(location)
                .build();
    }

    @Operation(
        summary = "Callback para o fluxo authorization code OAuth2 da autenticação do Google",
        description = "A página de autenticação/consentimento do Google redirecionará o usuário para este endpoint." 
    )
    @ApiResponse(responseCode = "204")
    @GetMapping("/oauth2/googlecalendar/callback")
    public ResponseEntity<Void> callbackGoogleCalendar(
        @RequestParam(name = "code") String code
    ) {
        String refreshToken = oauthProvider.getRefreshTokenFromCode(code);
        UUID currentUserId = UUID.fromString("cfd1cacd-fe74-4113-8c66-b84677237ea3"); // TODO, fix when auth works
        userRepository.updateExternalCalendarToken(currentUserId, refreshToken);
        
        return ResponseEntity.noContent().build();
    }
}