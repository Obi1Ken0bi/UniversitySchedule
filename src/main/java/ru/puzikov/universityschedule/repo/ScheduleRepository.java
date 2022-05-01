package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}