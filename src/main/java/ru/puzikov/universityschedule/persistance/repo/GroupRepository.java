package ru.puzikov.universityschedule.persistance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistance.model.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByNumber(int number);
}