package com.example.todo.infrastructure.calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.example.todo.infrastructure.calendar.oauth.GoogleOAuthProvider;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleCalendarService {
    
    private static final String CALENDAR_ID = "primary";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final GoogleOAuthProvider googleOAuthProvider;
    
    public Event insertEvent(Event event, String refreshToken) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService(refreshToken);
        return service.events().insert(CALENDAR_ID, event).execute();
    }
    
    public Event updateEvent(Event event, String eventId, String refreshToken) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService(refreshToken);
        return service.events().update(CALENDAR_ID, eventId, event).execute();
    }
    
    public void deleteEvent(String eventId, String refreshToken) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService(refreshToken);
        service.events().delete(CALENDAR_ID, eventId).execute();
    }
    
    public Event createGoogleEvent(String title, String description, DateTime startTime, DateTime endTime) {
        Event calendarEvent = new Event()
            .setSummary(title)
            .setDescription(description);
        
        EventDateTime start = new EventDateTime().setDateTime(startTime);
        calendarEvent.setStart(start);
        
        EventDateTime end = new EventDateTime().setDateTime(endTime);
        calendarEvent.setEnd(end);
        
        return calendarEvent;
    }
    
    private Calendar getCalendarService(String refreshToken) throws IOException, GeneralSecurityException {
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        
        String accessToken = googleOAuthProvider.getAccessTokenFromRefreshToken(refreshToken);
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod())
            .setAccessToken(accessToken);
        
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
            .build();
    }
} 