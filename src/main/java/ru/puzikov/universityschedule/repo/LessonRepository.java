package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Lesson;
import ru.puzikov.universityschedule.model.Room;
import ru.puzikov.universityschedule.model.Subject;
import ru.puzikov.universityschedule.model.Teacher;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findByRoomAndTeacherAndSubject(Room room, Teacher teacher, Subject subject);
}