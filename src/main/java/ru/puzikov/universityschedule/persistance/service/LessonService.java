package ru.puzikov.universityschedule.persistance.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistance.model.Lesson;
import ru.puzikov.universityschedule.persistance.repo.LessonRepository;

@Service
public class LessonService {
    final
    LessonRepository repository;

    public LessonService(LessonRepository repository) {
        this.repository = repository;
    }

    public Lesson saveOrGet(Lesson lesson) {
        if (repository.findByRoomAndTeacherAndSubject(lesson.getRoom(), lesson.getTeacher(), lesson.getSubject()).isEmpty())
            return repository.save(lesson);
        return repository.findByRoomAndTeacherAndSubject(lesson.getRoom(), lesson.getTeacher(), lesson.getSubject()).get();
    }
}
