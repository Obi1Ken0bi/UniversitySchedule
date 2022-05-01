package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}