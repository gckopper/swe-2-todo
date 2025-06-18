package com.example.todo.infrastructure.model;

import com.example.todo.domain.model.User;

public class UserMapper {
    public static User toDomain(UserDto userDto) {
        return User.builder()
            .id(userDto.getId())
            .name(userDto.getName())
            .email(userDto.getEmail())
            .externalCalendarServiceToken((userDto.getExternalCalendarServiceToken()))
            .build();
    }
    public static UserDto toDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .externalCalendarServiceToken((user.getExternalCalendarServiceToken()))
            .build();
    }
}
