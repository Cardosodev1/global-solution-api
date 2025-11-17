package com.global.solution.api.analysis.rqrs;

import com.global.solution.api.skill.rs.SkillLearningRS;
import com.global.solution.api.skill.rs.SkillRS;

import java.time.LocalDateTime;
import java.util.Set;

public record JobAnalysisRS(Long id,
                            String jobTitle,
                            String jobDescription,
                            LocalDateTime createdAt,
                            Long idResume,
                            Set<SkillRS> matchingSkills,
                            Set<SkillLearningRS> missingSkills,
                            Set<SkillRS> extraSkills) {
}
