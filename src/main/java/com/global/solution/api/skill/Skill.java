package com.global.solution.api.skill;

import com.global.solution.api.resume.Resume;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skills_seq_generator")
    @SequenceGenerator(name = "skills_seq_generator", sequenceName = "skills_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany(mappedBy = "skills")
    private Set<Resume> resumes =  new HashSet<>();

}
