package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Day;
import ru.puzikov.universityschedule.persistence.repo.DayRepository;

@Service
public class DayServiceImpl implements DayService {
    final
    DayRepository repository;

    public DayServiceImpl(DayRepository repository) {
        this.repository = repository;
    }

    @Override
    public Day save(Day day) {
        return repository.save(day);
    }
}
