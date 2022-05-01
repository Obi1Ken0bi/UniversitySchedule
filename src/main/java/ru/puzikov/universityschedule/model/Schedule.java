package ru.puzikov.universityschedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_gen")
    @SequenceGenerator(name = "schedule_gen", sequenceName = "schedule_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany
    private List<Day> days;

    public Schedule(List<Day> days) {
        this.days = days;
    }
}
