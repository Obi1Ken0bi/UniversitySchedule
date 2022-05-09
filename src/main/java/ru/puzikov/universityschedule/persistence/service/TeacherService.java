package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.Teacher;

public interface TeacherService {
    Teacher findByNameAndSurname(String name, String surname);

    Teacher saveOrGet(Teacher teacher);
}
