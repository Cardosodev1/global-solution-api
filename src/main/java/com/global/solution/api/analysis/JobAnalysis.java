package com.global.solution.api.analysis;

import com.global.solution.api.resume.Resume;
import com.global.solution.api.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gs_job_analyses")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class JobAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_analysis_seq_generator")
    @SequenceGenerator(name = "job_analysis_seq_generator", sequenceName = "job_analysis_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String jobTitle;

    @Column(nullable = false)
    @NotBlank
    private String jobDescription;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_resume")
    private Resume resume;

    @OneToMany(mappedBy = "jobAnalysis", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AnalysisResult> analysisResults = new HashSet<>();

    public JobAnalysis(String jobTitle, String jobDescription, User user, Resume resume) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.user = user;
        this.resume = resume;
    }

    public void setAnalysisResults(Set<AnalysisResult> analysisResults) {
        this.analysisResults.clear();
        if (analysisResults != null) {
            this.analysisResults.addAll(analysisResults);
        }
    }

}
