package ru.puzikov.universityschedule.persistance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistance.model.Lesson;
import ru.puzikov.universityschedule.persistance.model.Room;
import ru.puzikov.universityschedule.persistance.model.Subject;
import ru.puzikov.universityschedule.persistance.model.Teacher;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findByRoomAndTeacherAndSubject(Room room, Teacher teacher, Subject subject);
}