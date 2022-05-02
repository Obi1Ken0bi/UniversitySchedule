package ru.puzikov.universityschedule.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistence.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}