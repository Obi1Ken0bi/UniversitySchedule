package ru.puzikov.universityschedule.model;

import lombok.Getter;

@Getter
public enum DayOfWeek {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота");


    private final String representation;

    DayOfWeek(String representation) {
        this.representation = representation;
    }
}
