package com.global.solution.api.resume;

import com.global.solution.api.client.SkillExtractionService;
import com.global.solution.api.exception.ResourceNotFoundException;
import com.global.solution.api.resume.rqrs.ResumeRQ;
import com.global.solution.api.resume.rqrs.ResumeRS;
import com.global.solution.api.resume.rqrs.ResumeUpdateRQ;
import com.global.solution.api.skill.Skill;
import com.global.solution.api.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final SkillExtractionService skillExtractionService;

    @Transactional
    public ResumeRS createResume(ResumeRQ resumeRQ, User user) {
        Resume resume = new Resume(resumeRQ.title(), resumeRQ.description(), user);
        String textForAnalysis = resumeRQ.title() + " - " + resumeRQ.description();
        Set<Skill> skillsFromAI = skillExtractionService.findSkillsFromAI(textForAnalysis);
        if (!skillsFromAI.isEmpty()) {
            resume.updateSkills(skillsFromAI);
        }
        resumeRepository.save(resume);
        return new ResumeRS(resume);
    }

    public Page<ResumeRS> listMyResumes(User user, Pageable pageable) {
        Page<Resume> resumePage = resumeRepository.findByUserWithSkills(user, pageable);
        return resumePage.map(ResumeRS::new);
    }

    @Cacheable(value = "resumeById", key = "#id")
    public ResumeRS getMyResumeById(Long id, User user) {
        Resume resume = findResumeByIdAndUser(id, user);
        return new ResumeRS(resume);
    }

    @Transactional
    @CacheEvict(value = "resumeById", key = "#id")
    public ResumeRS updateMyResume(Long id, ResumeUpdateRQ resumeUpdateRQ, User user) {
        Resume resume = findResumeByIdAndUser(id, user);
        String newTitle = (resumeUpdateRQ.title() != null) ? resumeUpdateRQ.title() : resume.getTitle();
        String newDesc = (resumeUpdateRQ.description() != null) ? resumeUpdateRQ.description() : resume.getDescription();
        String newText = newTitle + " - " + newDesc;
        resume.update(resumeUpdateRQ);
        if (!resume.getDescription().equals(newDesc)) {
            Set<Skill> skillsFromAI = skillExtractionService.findSkillsFromAI(newText);
            resume.updateSkills(skillsFromAI);
        }
        return new ResumeRS(resume);
    }

    @Transactional
    @CacheEvict(value = "resumeById", key = "#id")
    public void deleteMyResume(Long id, User user) {
        Resume resume = findResumeByIdAndUser(id, user);
        resumeRepository.delete(resume);
    }

    private Resume findResumeByIdAndUser(Long id, User user) {
        return resumeRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Currículo não encontrado ou não pertence a este usuário. ID: " + id));
    }

}
