package ru.puzikov.universityschedule.persistance.model;

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

    public static DayOfWeek getDayOfWeek(int i) {
        switch (i) {
            case 0:
                return MONDAY;
            case 1:
                return TUESDAY;
            case 2:
                return WEDNESDAY;
            case 3:
                return THURSDAY;
            case 4:
                return FRIDAY;
            case 5:
                return SATURDAY;

        }
        return null;
    }
}
