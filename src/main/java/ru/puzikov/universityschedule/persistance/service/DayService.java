package ru.puzikov.universityschedule.persistance.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistance.model.Day;
import ru.puzikov.universityschedule.persistance.repo.DayRepository;

@Service
public class DayService {
    final
    DayRepository repository;

    public DayService(DayRepository repository) {
        this.repository = repository;
    }

    public Day save(Day day) {
        return repository.save(day);
    }
}
