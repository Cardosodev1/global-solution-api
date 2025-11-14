package com.global.solution.api.skill;

public record SkillRS(Long id,
                      String name,
                      Category category) {

    public SkillRS(Skill skill) {
        this(skill.getId(), skill.getName(), skill.getCategory());
    }

}
