package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.Lesson;

public interface LessonService {
    Lesson saveOrGet(Lesson lesson);
}
