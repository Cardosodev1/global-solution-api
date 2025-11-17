package com.global.solution.api.analysis.rqrs;

import com.global.solution.api.analysis.JobAnalysis;

import java.time.LocalDateTime;

public record JobAnalysisListRS(Long id,
                                String jobTitle,
                                LocalDateTime createdAt,
                                Long idResume) {

    public JobAnalysisListRS(JobAnalysis jobAnalysis) {
        this(jobAnalysis.getId(), jobAnalysis.getJobTitle(), jobAnalysis.getCreatedAt(), jobAnalysis.getResume().getId());
    }

}
