package com.example.todo.infrastructure.security;

import com.example.todo.domain.repository.UserRepositoryPort;
import com.example.todo.infrastructure.repository.UserRepositoryAdapter;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserRepositoryPort userRepository;

    PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.todo.domain.model.User> user = userRepository.findByUsername(username);
        // TODO trocar para senha do usuário
        return User.withUsername(username).password(passwordEncoder.encode(user.get().getEmail())).build();
    }
}