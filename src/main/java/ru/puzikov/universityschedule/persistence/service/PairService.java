package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Pair;
import ru.puzikov.universityschedule.persistence.repo.PairRepository;

@Service
public class PairService {
    final
    PairRepository repository;

    public PairService(PairRepository repository) {
        this.repository = repository;
    }

    public Pair saveOrGet(Pair pair) {
        if (repository.findByUpperLessonAndTimeAndDownLesson(
                        pair.getUpperLesson(),
                        pair.getTime(),
                        pair.getDownLesson())
                .isEmpty())
            return repository.save(pair);
        return repository
                .findByUpperLessonAndTimeAndDownLesson(pair.getUpperLesson(),
                        pair.getTime(),
                        pair.getDownLesson())
                .get();
    }
}