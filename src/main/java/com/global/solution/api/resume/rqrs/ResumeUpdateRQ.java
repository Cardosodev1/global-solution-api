package com.global.solution.api.resume.rqrs;

import jakarta.validation.constraints.Size;

public record ResumeUpdateRQ(

        @Size(min = 3, message = "Nome deve ter ao menos 3 caracteres")
        String title,

        @Size(min = 20, message = "Descrição deve ter ao menos 20 caracteres")
        String description

) {
}
