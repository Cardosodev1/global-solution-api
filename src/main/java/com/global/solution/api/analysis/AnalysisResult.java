package com.global.solution.api.analysis;

import com.global.solution.api.skill.Skill;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "gs_analysis_results")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "analysis_result_seq_generator")
    @SequenceGenerator(name = "analysis_result_seq_generator", sequenceName = "analysis_result_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_skill")
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_job_analysis")
    private JobAnalysis jobAnalysis;

}
