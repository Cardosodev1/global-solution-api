package com.global.solution.api.resume.rqrs;

import com.global.solution.api.resume.Resume;
import com.global.solution.api.skill.rs.SkillRS;

import java.util.Set;
import java.util.stream.Collectors;

public record ResumeRS(Long id,
                       String title,
                       String description,
                       Set<SkillRS> skills) {

    public ResumeRS(Resume resume) {
        this(resume.getId(), resume.getTitle(), resume.getDescription(), resume.getSkills()
                .stream()
                .map(SkillRS::new)
                .collect(Collectors.toSet())
        );
    }

}
