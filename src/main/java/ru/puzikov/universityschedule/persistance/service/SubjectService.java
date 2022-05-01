package ru.puzikov.universityschedule.persistance.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistance.model.Subject;
import ru.puzikov.universityschedule.persistance.repo.SubjectRepository;

@Service
public class SubjectService {
    final
    SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public Subject saveOrGet(Subject subject) {
        if (repository.findByName(subject.getName()).isEmpty()) {
            return repository.save(subject);
        }
        return repository.findByName(subject.getName()).get();
    }
}
