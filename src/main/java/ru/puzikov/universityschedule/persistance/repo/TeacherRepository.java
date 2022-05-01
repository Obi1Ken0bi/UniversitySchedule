package ru.puzikov.universityschedule.persistance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistance.model.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByNameAndSurname(String name, String surname);

    Optional<Teacher> findByNameAndSurname(String name, String surname);

}