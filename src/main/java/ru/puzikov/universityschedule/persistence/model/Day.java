package ru.puzikov.universityschedule.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Day {
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "day_pairs")
    List<Pair> pairs;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_gen")
    @SequenceGenerator(name = "day_gen", sequenceName = "day_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @ManyToMany(mappedBy = "days")
    private List<Schedule> schedule;

    public Day(DayOfWeek dayOfWeek, List<Pair> pairs) {
        this.dayOfWeek = dayOfWeek;
        this.pairs = pairs;
    }

    public Collection<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
