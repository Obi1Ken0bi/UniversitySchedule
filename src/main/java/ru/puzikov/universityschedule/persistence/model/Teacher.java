package ru.puzikov.universityschedule.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_gen")
    @SequenceGenerator(name = "teacher_gen", sequenceName = "teacher_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String surname;

    private String name;

    private String patronymic;

    public Teacher(String surname, String name, String patronymic) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

}
