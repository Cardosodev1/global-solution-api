package com.global.solution.api.analysis.rqrs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record JobAnalysisRQ(

        @NotNull(message = "{job.title.notblank}")
        @NotBlank(message = "{job.title.notblank}")
        @Size(min = 3, message = "{job.title.size}")
        String jobTitle,

        @NotNull(message = "{job.description.notblank}")
        @NotBlank(message = "{job.description.notblank}")
        @Size(min = 20, message = "{job.description.size}")
        String jobDescription,

        @NotNull(message = "{job.idresume.notnull}")
        Long idResume

) {
}
