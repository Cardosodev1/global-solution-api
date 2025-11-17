package com.global.solution.api.resume.rqrs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResumeRQ(

        @NotNull(message = "{resume.title.notblank}")
        @NotBlank(message = "{resume.title.notblank}")
        @Size(min = 3, message = "{resume.title.size}")
        String title,

        @NotNull(message = "{resume.description.notblank}")
        @NotBlank(message = "{resume.description.notblank}")
        @Size(min = 20, message = "{resume.description.size}")
        String description

) {
}
