package com.example.todo.infrastructure.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SecretKey signingKey;

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestHeader HttpHeaders headers) {
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String[] login = authHeader.split(" ");
        if (!login[0].equals("Basic")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        byte[] userInfoBytes = Base64.getDecoder().decode(login[1]);
        String userInfo = new String(userInfoBytes, StandardCharsets.UTF_8);
        String[] usernameAndPassword = userInfo.split(":");
        Authentication authentication =
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameAndPassword[0],
            usernameAndPassword[1]));
        if (!authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetails user = ((UserDetails) authentication.getPrincipal());
        String jwt =
            Jwts.builder().subject(user.getUsername()).issuedAt(new Date()).signWith(signingKey).compact();
        return ResponseEntity.ok(jwt);
    }
}
