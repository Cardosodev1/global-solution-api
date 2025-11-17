package com.global.solution.api.resume.rqrs;

import jakarta.validation.constraints.Size;

public record ResumeUpdateRQ(

        @Size(min = 3, message = "{resume.title.size}")
        String title,

        @Size(min = 20, message = "{resume.description.size}")
        String description

) {
}
