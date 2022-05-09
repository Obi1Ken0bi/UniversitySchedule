package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Subject;
import ru.puzikov.universityschedule.persistence.repo.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
    final
    SubjectRepository repository;

    public SubjectServiceImpl(SubjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Subject saveOrGet(Subject subject) {
        if (repository.findByName(subject.getName()).isEmpty()) {
            return repository.save(subject);
        }
        return repository.findByName(subject.getName()).get();
    }
}
