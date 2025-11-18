package com.global.solution.api.client;

import com.global.solution.api.client.rqrs.SkillExtractRS;
import com.global.solution.api.skill.Skill;
import com.global.solution.api.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillExtractionService {

    private final SkillApiClient skillApiClient;
    private final SkillRepository skillRepository;

    @Cacheable("aiSkillExtraction")
    public Set<Skill> findSkillsFromAI(String textForAnalysis) {
        SkillExtractRS skillExtractRS = skillApiClient.extractSkills(textForAnalysis);
        List<String> skillNamesFromAI = skillExtractRS.getHabilidades();
        if (skillNamesFromAI == null || skillNamesFromAI.isEmpty()) {
            log.warn("IA não retornou skills para o texto.");
            return Set.of();
        }
        List<Skill> existingSkills = skillRepository.findByNameIn(skillNamesFromAI);
        if (existingSkills.isEmpty()) {
            log.warn("IA retornou skills, mas nenhuma foi encontrada no banco: {}", skillNamesFromAI);
            return Set.of();
        }
        return Set.copyOf(existingSkills);
    }

}
