package ru.puzikov.universityschedule.persistance.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Day {
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
            @JoinTable(name = "day_pairs")
    List<Pair> pairs;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_gen")
    @SequenceGenerator(name = "day_gen", sequenceName = "day_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;


    public Day(DayOfWeek dayOfWeek, List<Pair> pairs) {
        this.dayOfWeek = dayOfWeek;
        this.pairs = pairs;
    }
}
