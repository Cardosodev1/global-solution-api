package com.global.solution.api.user.rqrs;

import com.global.solution.api.user.User;

import java.time.LocalDateTime;

public record UserRS(Long id,
                     String name,
                     String email,
                     LocalDateTime createdAt) {

    public UserRS(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }

}
