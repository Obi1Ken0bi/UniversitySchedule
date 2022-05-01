package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Day;

public interface DayRepository extends JpaRepository<Day, Long> {
}