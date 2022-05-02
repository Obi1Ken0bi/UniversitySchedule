package ru.puzikov.universityschedule.persistance.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_gen")
    @SequenceGenerator(name = "schedule_gen", sequenceName = "schedule_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Day> days;

    public Schedule(List<Day> days) {
        this.days = days;
    }


}
