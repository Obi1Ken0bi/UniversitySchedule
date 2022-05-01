package ru.puzikov.universityschedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.model.Subject;
import ru.puzikov.universityschedule.repo.SubjectRepository;

@Service
public class SubjectService {
    final
    SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public Subject saveOrGet(Subject subject){
        return repository.findByName(subject.getName()).orElse(repository.save(subject));
    }
}
