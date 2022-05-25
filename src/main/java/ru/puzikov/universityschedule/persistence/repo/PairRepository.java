package ru.puzikov.universityschedule.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.puzikov.universityschedule.persistence.model.Lesson;
import ru.puzikov.universityschedule.persistence.model.Pair;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface PairRepository extends JpaRepository<Pair, Long> {
    Optional<Pair> findByUpperLessonAndTimeAndDownLesson(Lesson upperLesson, LocalTime time, Lesson downLesson);


    @Query(value = "select distinct time from Pair")
    List<LocalTime> findDistinctTimes();
}