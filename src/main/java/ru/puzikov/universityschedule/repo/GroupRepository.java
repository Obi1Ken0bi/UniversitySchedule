package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}