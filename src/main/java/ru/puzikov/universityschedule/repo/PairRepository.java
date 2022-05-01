package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Lesson;
import ru.puzikov.universityschedule.model.Pair;

import java.time.LocalTime;
import java.util.Optional;

public interface PairRepository extends JpaRepository<Pair, Long> {
    Optional<Pair> findByUpperLessonAndTimeAndDownLesson(Lesson upperLesson, LocalTime time, Lesson downLesson);
}