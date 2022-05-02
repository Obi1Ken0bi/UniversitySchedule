package ru.puzikov.universityschedule.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistence.model.Lesson;
import ru.puzikov.universityschedule.persistence.model.Pair;

import java.time.LocalTime;
import java.util.Optional;

public interface PairRepository extends JpaRepository<Pair, Long> {
    Optional<Pair> findByUpperLessonAndTimeAndDownLesson(Lesson upperLesson, LocalTime time, Lesson downLesson);
}