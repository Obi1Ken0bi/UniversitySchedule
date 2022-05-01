package ru.puzikov.universityschedule.persistance.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistance.model.Teacher;
import ru.puzikov.universityschedule.persistance.repo.TeacherRepository;

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
            return repository.findByNameAndSurname(teacher.getName(), teacher.getSurname()).orElseThrow(RuntimeException::new);
        }
        return repository.save(teacher);
    }
}
