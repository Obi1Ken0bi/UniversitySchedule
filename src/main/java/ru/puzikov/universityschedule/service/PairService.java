package ru.puzikov.universityschedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.model.Pair;
import ru.puzikov.universityschedule.repo.PairRepository;

@Service
public class PairService {
    final
    PairRepository repository;

    public PairService(PairRepository repository) {
        this.repository = repository;
    }

    public Pair saveOrGet(Pair pair){
       return repository.findByUpperLessonAndTimeAndDownLesson(
                pair.getUpperLesson(),
                pair.getTime(),
                pair.getDownLesson())
                .orElse(repository.save(pair));
    }
}
