package ru.puzikov.universityschedule.model;

import lombok.*;
import org.hibernate.mapping.Collection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_gen")
    @SequenceGenerator(name = "day_gen", sequenceName = "day_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @ManyToMany
    List<Pair> pairs;


    public Day(DayOfWeek dayOfWeek, List<Pair> pairs) {
        this.dayOfWeek = dayOfWeek;
        this.pairs = pairs;
    }
}
