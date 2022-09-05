package ru.puzikov.universityschedule.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.extern.slf4j.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class Pair {
    @ManyToMany(mappedBy = "pairs")
    @JsonIgnore
    List<Day> days;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pair_gen")
    @SequenceGenerator(name = "pair_gen", sequenceName = "pair_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "upper_lesson_id")
    private Lesson upperLesson;

    private LocalTime time;

    private boolean weekDependent;

    @ManyToOne(cascade = CascadeType.REMOVE)
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

    public int getRange(LocalTime time) {
        return getTime().toSecondOfDay() - time.toSecondOfDay();
    }

    public Lesson getLesson() {
        LocalDateTime upperWeekDate = LocalDateTime.of(LocalDate.now().getYear(), 8, 29, 0, 5);
        LocalDateTime now = LocalDateTime.now();
        Lesson lesson;
        log.info(isWeekDependent());
        if (!isWeekDependent()) {
            lesson = getUpperLesson();
        } else {
            Integer daysBetween = Duration.between(upperWeekDate, now).toDays();
            log.info(daysBetween);
            if (daysBetween % 2 == 0)
            lesson = getUpperLesson();
        else
            lesson = getDownLesson();
        }
        return lesson;
    }

}
