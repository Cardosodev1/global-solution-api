package com.global.solution.api.user;

import java.time.LocalDateTime;

public record UserDTO(Long id,
                      String name,
                      String email,
                      LocalDateTime createdAt) {

    public UserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }

}
