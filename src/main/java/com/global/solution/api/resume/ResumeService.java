package com.global.solution.api.resume;

import com.global.solution.api.client.SkillApiClient;
import com.global.solution.api.client.rqrs.SkillExtractRS;
import com.global.solution.api.exception.ResourceNotFoundException;
import com.global.solution.api.skill.Skill;
import com.global.solution.api.skill.SkillRepository;
import com.global.solution.api.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final SkillRepository skillRepository;
    private final SkillApiClient  skillApiClient;

    @Transactional
    public ResumeRS createResume(ResumeRQ resumeRQ, User user) {
        Resume resume = new Resume(resumeRQ.title(), resumeRQ.description(), user);
        String textForAnalysis = resumeRQ.title() + " - " + resumeRQ.description();
        Set<Skill> skillsFromAI = this.findSkillsFromAI(textForAnalysis);
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

    public ResumeRS getMyResumeById(Long id, User user) {
        Resume resume = findResumeByIdAndUser(id, user);
        return new ResumeRS(resume);
    }

    @Transactional
    public ResumeRS updateMyResume(Long id, ResumeUpdateRQ resumeUpdateRQ, User user) {
        Resume resume = findResumeByIdAndUser(id, user);
        String oldText = resume.getTitle() + " - " + resume.getDescription();

        String newTitle = (resumeUpdateRQ.title() != null) ? resumeUpdateRQ.title() : resume.getTitle();
        String newDesc = (resumeUpdateRQ.description() != null) ? resumeUpdateRQ.description() : resume.getDescription();
        String newText = newTitle + " - " + newDesc;

        resume.update(resumeUpdateRQ);
        if (!oldText.equals(newText)) {
            Set<Skill> skillsFromAI = this.findSkillsFromAI(newText);
            resume.updateSkills(skillsFromAI);
        }
        return new ResumeRS(resume);
    }

    @Transactional
    public void deleteMyResume(Long id, User user) {
        Resume resume = findResumeByIdAndUser(id, user);
        resumeRepository.delete(resume);
    }

    private Set<Skill> findSkillsFromAI(String textForAnalysis) {
        SkillExtractRS skillExtractRS = skillApiClient.extractSkills(textForAnalysis);
        List<String> skillNamesFromAI = skillExtractRS.getSkills();
        if (skillNamesFromAI == null || skillNamesFromAI.isEmpty()) {
            System.out.println("IA não retornou skills.");
            return Set.of();
        }
        List<Skill> existingSkills = skillRepository.findByNameIn(skillNamesFromAI);
        if (existingSkills.isEmpty()) {
            System.out.println("IA retornou skills, mas nenhuma foi encontrada no banco.");
            return Set.of();
        }
        return Set.copyOf(existingSkills);
    }

    private Resume findResumeByIdAndUser(Long id, User user) {
        return resumeRepository.findByIdAndUser(id, user).orElseThrow(() -> new ResourceNotFoundException("Currículo não encontrado ou não pertence a este usuário. ID: " + id));
    }

}
