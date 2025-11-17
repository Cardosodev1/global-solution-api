package com.global.solution.api.skill.rs;

import com.global.solution.api.skill.Skill;
import com.global.solution.api.skill.enums.Category;

import java.util.Set;
import java.util.stream.Collectors;

public record SkillLearningRS(Long id,
                              String name,
                              Category category,
                              Set<LearningResourceRS> learningResources) {

    public SkillLearningRS(Skill skill) {
        this(skill.getId(), skill.getName(), skill.getCategory(), skill.getLearningResources()
                .stream()
                .map(LearningResourceRS::new)
                .collect(Collectors.toSet())
        );
    }

}
