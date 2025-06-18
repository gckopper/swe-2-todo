package com.example.todo.infrastructure.model;

import com.example.todo.domain.model.User;

public class UserMapper {
    public static User toDomain(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return User.builder()
            .id(userDto.getId())
            .name(userDto.getName())
            .email(userDto.getEmail())
            .externalCalendarServiceToken((userDto.getExternalCalendarServiceToken()))
            .build();
    }
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .externalCalendarServiceToken((user.getExternalCalendarServiceToken()))
            .build();
    }
}
