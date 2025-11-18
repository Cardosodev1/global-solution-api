package com.global.solution.api.resume;

import com.global.solution.api.analysis.JobAnalysis;
import com.global.solution.api.resume.rqrs.ResumeUpdateRQ;
import com.global.solution.api.skill.Skill;
import com.global.solution.api.user.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gs_resumes")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_seq_generator")
    @SequenceGenerator(name = "resume_seq_generator", sequenceName = "resume_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false, length = 4000)
    @NotBlank
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gs_resumes_skills", joinColumns = @JoinColumn(name = "id_resume"), inverseJoinColumns = @JoinColumn(name = "id_skill"))
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<JobAnalysis> jobAnalyses = new HashSet<>();

    public Resume(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public void update(@Valid ResumeUpdateRQ request) {
        if (request.title() != null) {
            this.title = request.title();
        }
        if (request.description() != null) {
            this.description = request.description();
        }
    }

    public void updateSkills(Set<Skill> skillsFromAI) {
        this.skills.clear();
        if (skillsFromAI != null) {
            this.skills.addAll(skillsFromAI);
        }
    }

}
