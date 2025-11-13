package com.global.solution.api.skills;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skills_seq_generator")
    @SequenceGenerator(name = "skills_seq_generator", sequenceName = "skills_seq", allocationSize = 1)
    private Long id;

    private String name;

    private Category category;

}
