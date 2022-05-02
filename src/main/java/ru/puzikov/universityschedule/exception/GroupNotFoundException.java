package ru.puzikov.universityschedule.exception;

public class GroupNotFoundException extends Exception {

    public GroupNotFoundException() {
        super("Группа не найдена");
    }
}
