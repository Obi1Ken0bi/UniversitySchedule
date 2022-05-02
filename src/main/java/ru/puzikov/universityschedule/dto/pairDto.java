package ru.puzikov.universityschedule.dto;

import org.hibernate.type.LocaleType;
import ru.puzikov.universityschedule.persistance.model.Lesson;

import java.time.LocalTime;

public class pairDto {
    private Lesson lesson;

    private LocalTime time;
}
