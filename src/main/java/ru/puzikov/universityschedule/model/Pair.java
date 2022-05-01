package ru.puzikov.universityschedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pair {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pair_gen")
    @SequenceGenerator(name = "pair_gen", sequenceName = "pair_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "upper_lesson_id")
    private Lesson upperLesson;

    private LocalTime time;

    private boolean weekDependent;

    @ManyToOne
    @JoinColumn(name = "down_lesson_id")
    private Lesson downLesson;


    public Pair(Lesson upperLesson, LocalTime time) {
        this.upperLesson = upperLesson;
        this.downLesson=upperLesson;
        this.time = time;
        this.weekDependent=false;
    }

    public Pair(Lesson upperLesson, Lesson downLesson,LocalTime time) {
        this.upperLesson = upperLesson;
        this.time = time;
        this.downLesson = downLesson;
        this.weekDependent=true;
    }

    public Lesson getLesson(boolean upperWeek){
        if(upperWeek)
            return this.upperLesson;
        return this.downLesson;
    }
}
