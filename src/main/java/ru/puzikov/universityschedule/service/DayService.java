package ru.puzikov.universityschedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.model.Day;
import ru.puzikov.universityschedule.repo.DayRepository;

@Service
public class DayService {
    final
    DayRepository repository;

    public DayService(DayRepository repository) {
        this.repository = repository;
    }

    public Day save(Day day){
       return repository.save(day);
    }
}
