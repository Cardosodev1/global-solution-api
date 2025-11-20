package com.global.solution.api.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.global.solution.api.analysis.AnalysisResult;
import com.global.solution.api.resume.Resume;
import com.global.solution.api.skill.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gs_skills")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_seq_generator")
    @SequenceGenerator(name = "skill_seq_generator", sequenceName = "skill_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(length = 20)
    private String acronym;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<LearningResource> learningResources = new HashSet<>();

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<AnalysisResult> analysisResults = new HashSet<>();

    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private Set<Resume> resumes =  new HashSet<>();

}
