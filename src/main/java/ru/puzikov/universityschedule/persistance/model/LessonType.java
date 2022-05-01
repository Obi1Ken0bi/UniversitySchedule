package ru.puzikov.universityschedule.persistance.model;

import lombok.Getter;

@Getter
public enum LessonType {
    PRACTICE("Практическое занятие"), LECTURE("Лекция"), LAB("Лабораторное занятие");

    private final String representation;

    LessonType(String representation) {
        this.representation = representation;
    }

    public static LessonType getFromRepresentation(String representation) {
        switch (representation) {
            case "Практическое занятие":
                return PRACTICE;
            case "Лекция":
                return LECTURE;
            case "Лабораторное занятие":
                return LAB;

        }
        return null;
    }
}
