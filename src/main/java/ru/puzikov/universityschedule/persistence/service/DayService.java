package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Day;
import ru.puzikov.universityschedule.persistence.repo.DayRepository;

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
