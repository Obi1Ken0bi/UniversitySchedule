package ru.puzikov.universityschedule.telegram.message;

import org.springframework.stereotype.Service;

@Service
public class StartMessageProcessor implements MessageProcessor {
    private final String START_TEXT = "Добро пожаловать в бота для отслежования расписания, для регистрации введите номер своей группы. Помощь доступна по команде /help";

    @Override
    public String execute(String message, String chatId) {
        return START_TEXT;
    }
}
