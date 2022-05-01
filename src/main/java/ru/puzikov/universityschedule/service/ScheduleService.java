package ru.puzikov.universityschedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.model.Schedule;
import ru.puzikov.universityschedule.repo.ScheduleRepository;

@Service
public class ScheduleService {
    final
    ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public Schedule save(Schedule schedule){
        return repository.save(schedule);
    }
}
