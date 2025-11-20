package com.global.solution.api.analysis.rqrs;

import com.global.solution.api.analysis.enums.JobAnalysisStatus;

public record JobAnalysisStatusRS(Long id,
                                  JobAnalysisStatus status) {
}
