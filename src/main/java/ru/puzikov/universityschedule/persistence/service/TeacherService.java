package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Teacher;
import ru.puzikov.universityschedule.persistence.repo.TeacherRepository;

@Service
public class TeacherService {
    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public Teacher findByNameAndSurname(String name, String surname) {
        return repository.findByNameAndSurname(name, surname).orElseThrow(RuntimeException::new);
    }

    public Teacher saveOrGet(Teacher teacher) {
        if (repository.existsByNameAndSurname(teacher.getName(), teacher.getSurname())) {
            return repository.findByNameAndSurname(teacher.getName(), teacher.getSurname())
                    .orElseThrow(RuntimeException::new);
        }
        return repository.save(teacher);
    }
}
