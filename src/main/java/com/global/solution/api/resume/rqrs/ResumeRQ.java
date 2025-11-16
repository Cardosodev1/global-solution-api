package com.global.solution.api.resume.rqrs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResumeRQ(

        @NotNull
        @NotBlank
        @Size(min = 3, message = "Título deve ter ao menos 3 caracteres")
        String title,

        @NotNull
        @NotBlank
        @Size(min = 20, message = "Descrição deve ter ao menos 20 caracteres")
        String description

) {
}
