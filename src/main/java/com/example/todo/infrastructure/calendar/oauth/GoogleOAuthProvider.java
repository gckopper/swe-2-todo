package com.example.todo.infrastructure.calendar.oauth;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.todo.infrastructure.config.GoogleCalendarConfig;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.UserCredentials;

@Component
public class GoogleOAuthProvider {
    private final GoogleCalendarConfig config;
    private final List<String> scopes = List.of("https://www.googleapis.com/auth/calendar");

    public GoogleOAuthProvider(GoogleCalendarConfig config) {
        this.config = config;
    }

    public URI getAuthorizationCodeUrl() {
        GoogleAuthorizationCodeRequestUrl urlBuilder =
            new GoogleAuthorizationCodeRequestUrl(
                config.getClientId(),
                config.getRedirectUri(),
                this.scopes
            )
            .setAccessType("offline")
            .set("prompt", "select_account consent");

        return URI.create(urlBuilder.build());
    }


    public String getRefreshTokenFromCode(String code) {
        JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        HttpTransport HTTP_TRANSPORT;

        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException ex) {
            return null; // TODO
        }

        GoogleTokenResponse tokenResponse;
        try {
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    HTTP_TRANSPORT,
                    JSON_FACTORY,
                    config.getClientId(),
                    config.getClientSecret(),
                    code,
                    config.getRedirectUri()
            ).execute();
        } catch (IOException ex) {
            // TODO
            return null;
        }

        return tokenResponse.getRefreshToken();
    }

    public String getAccessTokenFromRefreshToken(String refreshToken) {
        try {
            GoogleCredentials credentials = UserCredentials.newBuilder()
                .setClientId(config.getClientId())
                .setClientSecret(config.getClientSecret())
                .setRefreshToken(refreshToken)
                .build();
            
            credentials.refreshIfExpired();
            return credentials.getAccessToken().getTokenValue();
        } catch (IOException ex) {
            return null; // TODO
        }
    }
}