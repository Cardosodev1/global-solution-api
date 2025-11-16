package com.global.solution.api.security.rqrs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRQ(

        @NotBlank
        @Email
        String email,

        @NotNull
        @NotBlank
        String password

) {
}
