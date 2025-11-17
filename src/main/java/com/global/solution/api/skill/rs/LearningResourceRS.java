package com.global.solution.api.skill.rs;

import com.global.solution.api.skill.LearningResource;
import com.global.solution.api.skill.enums.DifficultyLevel;
import com.global.solution.api.skill.enums.ResourceType;

public record LearningResourceRS(String title,
                                 String url,
                                 ResourceType resourceType,
                                 DifficultyLevel difficultyLevel) {

    public LearningResourceRS(LearningResource learningResource) {
        this(learningResource.getTitle(), learningResource.getUrl(), learningResource.getResourceType(), learningResource.getDifficultyLevel());
    }

}
