package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Subject;
import ru.puzikov.universityschedule.persistence.repo.SubjectRepository;

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
