package com.global.solution.api.user.rqrs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRQ(

        @Size(min = 3, message = "{user.name.size}")
        String name,

        @Email(message = "{user.email.invalid}")
        String email,

        @Size(min = 8, message = "{user.password.size}")
        String password

) {
}
