package com.global.solution.api.analysis;

import com.global.solution.api.analysis.enums.Status;
import com.global.solution.api.analysis.rqrs.JobAnalysisListRS;
import com.global.solution.api.analysis.rqrs.JobAnalysisRQ;
import com.global.solution.api.analysis.rqrs.JobAnalysisRS;
import com.global.solution.api.client.SkillExtractionService;
import com.global.solution.api.exception.ResourceNotFoundException;
import com.global.solution.api.resume.Resume;
import com.global.solution.api.resume.ResumeRepository;
import com.global.solution.api.skill.Skill;
import com.global.solution.api.skill.SkillRepository;
import com.global.solution.api.skill.rs.SkillLearningRS;
import com.global.solution.api.skill.rs.SkillRS;
import com.global.solution.api.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobAnalysisService {

    private final JobAnalysisRepository jobAnalysisRepository;
    private final ResumeRepository resumeRepository;
    private final SkillRepository skillRepository;
    private final SkillExtractionService skillExtractionService;

    @Transactional
    public JobAnalysisRS createJobAnalysis(JobAnalysisRQ jobAnalysisRQ, User user) {
        Resume resumeWithSkills = resumeRepository.findByIdAndUserWithSkills(jobAnalysisRQ.idResume(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Currículo não encontrado ou não pertence a este usuário"));
        Set<Skill> resumeSkills = resumeWithSkills.getSkills();
        String jobText = jobAnalysisRQ.jobTitle() + " - " + jobAnalysisRQ.jobDescription();
        Set<Skill> jobSkills = skillExtractionService.findSkillsFromAI(jobText);
        JobAnalysis jobAnalysis = new JobAnalysis(jobAnalysisRQ.jobTitle(), jobAnalysisRQ.jobDescription(), user, resumeWithSkills);
        Set<AnalysisResult> analysisResults = calculateGapAnalysis(resumeSkills, jobSkills, jobAnalysis);
        jobAnalysis.setAnalysisResults(analysisResults);
        jobAnalysisRepository.save(jobAnalysis);
        return this.buildJobAnalysisRS(jobAnalysis);
    }

    public Page<JobAnalysisListRS> listMyAnalyses(User user, Pageable pageable) {
        Page<JobAnalysis> page = jobAnalysisRepository.findByUser(user, pageable);
        return page.map(JobAnalysisListRS::new);
    }

    public JobAnalysisRS getMyAnalysisById(Long id, User user) {
        JobAnalysis analysis = jobAnalysisRepository.findByIdAndUserWithResults(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Análise não encontrada ou não pertence a este usuário"));
        return this.buildJobAnalysisRS(analysis);
    }

    @Transactional
    public void deleteMyAnalysis(Long id, User user) {
        JobAnalysis analysis = jobAnalysisRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Análise não encontrada ou não pertence a este usuário"));
        jobAnalysisRepository.delete(analysis);
    }

    private Set<AnalysisResult> calculateGapAnalysis(Set<Skill> resumeSkills, Set<Skill> jobSkills, JobAnalysis jobAnalysis) {
        Set<AnalysisResult> analysisResults = new HashSet<>();
        Set<Skill> matchingSkills = new HashSet<>(resumeSkills);
        matchingSkills.retainAll(jobSkills);
        for (Skill skill : matchingSkills) {
            analysisResults.add(new AnalysisResult(Status.MATCH, skill, jobAnalysis));
        }
        Set<Skill> missingSkills = new HashSet<>(jobSkills);
        missingSkills.removeAll(resumeSkills);
        for (Skill skill : missingSkills) {
            analysisResults.add(new AnalysisResult(Status.GAP, skill, jobAnalysis));
        }
        Set<Skill> extraSkills = new HashSet<>(resumeSkills);
        extraSkills.removeAll(jobSkills);
        for (Skill skill : extraSkills) {
            analysisResults.add(new AnalysisResult(Status.EXTRA, skill, jobAnalysis));
        }
        return analysisResults;
    }

    private JobAnalysisRS buildJobAnalysisRS(JobAnalysis jobAnalysis) {
        Set<Skill> matchingSkills = jobAnalysis.getAnalysisResults().stream()
                .filter(ar -> ar.getStatus() == Status.MATCH)
                .map(AnalysisResult::getSkill)
                .collect(Collectors.toSet());

        Set<Skill> extraSkills = jobAnalysis.getAnalysisResults().stream()
                .filter(ar -> ar.getStatus() == Status.EXTRA)
                .map(AnalysisResult::getSkill)
                .collect(Collectors.toSet());

        Set<Skill> missingSkills = jobAnalysis.getAnalysisResults().stream()
                .filter(ar -> ar.getStatus() == Status.GAP)
                .map(AnalysisResult::getSkill)
                .collect(Collectors.toSet());

        Set<Long> missingSkillIds = missingSkills.stream()
                .map(Skill::getId)
                .collect(Collectors.toSet());

        Set<Skill> missingSkillsWithResources = skillRepository.findByIdWithLearningResources(missingSkillIds);

        Set<SkillRS> matchingSkillsRS = matchingSkills.stream()
                .map(SkillRS::new)
                .collect(Collectors.toSet());

        Set<SkillRS> extraSkillsRS = extraSkills.stream()
                .map(SkillRS::new)
                .collect(Collectors.toSet());

        Set<SkillLearningRS> missingSkillsRS = missingSkillsWithResources.stream()
                .map(SkillLearningRS::new)
                .collect(Collectors.toSet());

        return new JobAnalysisRS(jobAnalysis.getId(),
                jobAnalysis.getJobTitle(),
                jobAnalysis.getJobDescription(),
                jobAnalysis.getCreatedAt(),
                jobAnalysis.getResume().getId(),
                matchingSkillsRS,
                missingSkillsRS,
                extraSkillsRS
        );
    }

}