package ru.puzikov.universityschedule.telegram.message;

public interface MessageProcessor {
    String execute(String message, String chatId);
}
