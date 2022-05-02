package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Schedule;
import ru.puzikov.universityschedule.persistence.repo.ScheduleRepository;

@Service
public class ScheduleService {
    final
    ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public Schedule save(Schedule schedule) {
        return repository.save(schedule);
    }
}
