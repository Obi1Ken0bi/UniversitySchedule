package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Lesson;
import ru.puzikov.universityschedule.persistence.repo.LessonRepository;

@Service
public class LessonServiceImpl implements LessonService {
    final
    LessonRepository repository;

    public LessonServiceImpl(LessonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Lesson saveOrGet(Lesson lesson) {
        if (repository.findByRoomAndTeacherAndSubject(lesson.getRoom(),
                        lesson.getTeacher(),
                        lesson.getSubject())
                .isEmpty())
            return repository.save(lesson);
        return repository.findByRoomAndTeacherAndSubject(lesson.getRoom(),
                        lesson.getTeacher(),
                        lesson.getSubject())
                .get();
    }
}
