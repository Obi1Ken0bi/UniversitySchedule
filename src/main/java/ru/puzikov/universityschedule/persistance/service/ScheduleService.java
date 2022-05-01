package ru.puzikov.universityschedule.persistance.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistance.model.Schedule;
import ru.puzikov.universityschedule.persistance.repo.ScheduleRepository;

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
