package com.global.solution.api.skills;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gs_skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skills_seq_generator")
    @SequenceGenerator(name = "skills_seq_generator", sequenceName = "skills_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Category category;

}
