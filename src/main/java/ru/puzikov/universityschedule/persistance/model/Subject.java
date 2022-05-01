package ru.puzikov.universityschedule.persistance.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_gen")
    @SequenceGenerator(name = "subject_gen", sequenceName = "subject_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;


    public Subject(String name) {
        this.name = name;
    }
}
