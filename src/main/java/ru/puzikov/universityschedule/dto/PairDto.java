package ru.puzikov.universityschedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public boolean isTimeToNotify() {
        return getTime().toSecondOfDay() - LocalTime.now().toSecondOfDay() < 900;
    }

    public String toString() {
        return String.format("Следующее занятие: %s В кабинете %s%s, Время=%s)",
                this.getLesson().getSubject().getName(),
                this.getLesson().getRoom().getBuilding(),
                this.getLesson().getRoom().getNumber(),
                this.getTime());
    }
}
