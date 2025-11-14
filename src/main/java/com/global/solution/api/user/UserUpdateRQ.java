package com.global.solution.api.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRQ(

        @Size(min = 3, message = "Nome deve ter ao menos 3 caracteres")
        String name,

        @Email(message = "Email inválido")
        String email,

        @Size(min = 8, message = "Senha deve ter ao menos 8 caracteres")
        String password

) {
}
