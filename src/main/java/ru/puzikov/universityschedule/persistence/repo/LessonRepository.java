package ru.puzikov.universityschedule.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistence.model.Lesson;
import ru.puzikov.universityschedule.persistence.model.Room;
import ru.puzikov.universityschedule.persistence.model.Subject;
import ru.puzikov.universityschedule.persistence.model.Teacher;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findByRoomAndTeacherAndSubject(Room room, Teacher teacher, Subject subject);
}