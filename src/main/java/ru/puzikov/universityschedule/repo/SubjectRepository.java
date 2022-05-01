package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}