package com.global.solution.api.skill;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Cacheable("skillsByName")
    List<Skill> findByNameIn(Collection<String> names);

    @Query("SELECT s FROM Skill s LEFT JOIN FETCH s.learningResources WHERE s.id IN :skillIds")
    Set<Skill> findByIdWithLearningResources(Set<Long> skillIds);

}
