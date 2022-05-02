package ru.puzikov.universityschedule.persistance.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@ToString

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pair {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pair_gen")
    @SequenceGenerator(name = "pair_gen", sequenceName = "pair_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToMany(mappedBy = "pairs")
    List<Day> days;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "upper_lesson_id")
    private Lesson upperLesson;

    private LocalTime time;

    private boolean weekDependent;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "down_lesson_id")
    private Lesson downLesson;


    public Pair(Lesson upperLesson, LocalTime time) {
        this.upperLesson = upperLesson;
        this.downLesson = upperLesson;
        this.time = time;
        this.weekDependent = false;
    }

    public Pair(Lesson upperLesson, Lesson downLesson, LocalTime time) {
        this.upperLesson = upperLesson;
        this.time = time;
        this.downLesson = downLesson;
        this.weekDependent = true;
    }

    public Lesson getLesson(boolean upperWeek) {
        if (upperWeek)
            return this.upperLesson;
        return this.downLesson;
    }
}
