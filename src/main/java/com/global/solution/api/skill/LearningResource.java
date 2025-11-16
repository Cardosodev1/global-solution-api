package com.global.solution.api.skill;

import com.global.solution.api.skill.enums.DifficultyLevel;
import com.global.solution.api.skill.enums.ResourceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gs_learning_resources")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class LearningResource {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "learning_resource_seq_generator")
    @SequenceGenerator(name = "learning_resource_seq_generator", sequenceName = "learning_resource_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false)
    @NotBlank
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType resourceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficultyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_skill")
    private Skill skill;

}
