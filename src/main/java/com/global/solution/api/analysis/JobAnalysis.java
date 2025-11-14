package com.global.solution.api.analysis;

import com.global.solution.api.resume.Resume;
import com.global.solution.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "gs_job_analysis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class JobAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_analysis_seq_generator")
    @SequenceGenerator(name = "job_analysis_seq_generator", sequenceName = "job_analysis_seq", allocationSize = 1)
    private Long id;

    private String jobTitle;

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

}
