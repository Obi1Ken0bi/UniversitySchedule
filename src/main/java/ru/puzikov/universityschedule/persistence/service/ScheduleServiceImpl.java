package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Schedule;
import ru.puzikov.universityschedule.persistence.repo.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    final
    ScheduleRepository repository;

    public ScheduleServiceImpl(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Schedule save(Schedule schedule) {
        return repository.save(schedule);
    }
}
