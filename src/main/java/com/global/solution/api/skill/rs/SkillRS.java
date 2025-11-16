package com.global.solution.api.skill.rs;

import com.global.solution.api.skill.Skill;
import com.global.solution.api.skill.enums.Category;

public record SkillRS(Long id,
                      String name,
                      Category category) {

    public SkillRS(Skill skill) {
        this(skill.getId(), skill.getName(), skill.getCategory());
    }

}
