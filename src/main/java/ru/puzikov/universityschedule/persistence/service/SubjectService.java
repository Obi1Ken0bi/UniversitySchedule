package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.Subject;

public interface SubjectService {
    Subject saveOrGet(Subject subject);
}
