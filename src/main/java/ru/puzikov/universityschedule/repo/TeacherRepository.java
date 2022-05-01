package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}