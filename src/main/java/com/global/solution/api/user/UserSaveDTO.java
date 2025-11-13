package com.global.solution.api.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserSaveDTO(

        @NotNull
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotNull
        @NotBlank
        String password

) {
}
