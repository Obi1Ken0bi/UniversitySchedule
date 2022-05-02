package ru.puzikov.universityschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.puzikov.universityschedule.persistence.model.Lesson;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class PairDto {
    private Lesson lesson;

    private LocalTime time;
}
