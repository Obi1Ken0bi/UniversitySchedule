package ru.puzikov.universityschedule.model;

import lombok.Getter;

@Getter
public enum LessonType {
    PRACTICE("Практическое занятие"), LECTURE("Лекция"), LAB("Лабораторное занятие");

    private final String representation;
    LessonType(String representation){
        this.representation=representation;
    }
}
