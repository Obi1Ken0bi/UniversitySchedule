package ru.puzikov.universityschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.puzikov.universityschedule.model.*;
import ru.puzikov.universityschedule.service.*;

import javax.transaction.Transactional;

@SpringBootApplication
public class UniversityScheduleApplication {
    @Autowired
    LessonService lessonService;
    @Autowired
    RoomService roomService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(UniversityScheduleApplication.class, args);
    }
    @Bean
    @Transactional
    public CommandLineRunner runner(){
        return args -> {
            Room a = new Room(buildingService.saveOrGet(new Building('a')), "1");
            Subject math = new Subject("Математика");
            Teacher teacher = new Teacher("Pepega", "Pepega", "Pepega");
            a=roomService.saveOrGet(a);
            math=subjectService.saveOrGet(math);
            teacher=teacherService.saveOrGet(teacher);

            Lesson lesson=Lesson.builder()
                    .room(a)
                    .subject(math)
                    .teacher(teacher)
                    .type(LessonType.PRACTICE)
                    .build();
            lessonService.saveOrGet(lesson);
        };
    }
}
