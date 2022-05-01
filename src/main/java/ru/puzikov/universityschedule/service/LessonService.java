package ru.puzikov.universityschedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.model.Lesson;
import ru.puzikov.universityschedule.repo.LessonRepository;

@Service
public class LessonService {
    final
    LessonRepository repository;

    public LessonService(LessonRepository repository) {
        this.repository = repository;
    }

    public Lesson saveOrGet(Lesson lesson){
       return repository.findByRoomAndTeacherAndSubject(lesson.getRoom(),lesson.getTeacher(),lesson.getSubject())
                .orElse(repository.save(lesson));
    }
}
