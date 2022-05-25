package ru.puzikov.universityschedule.telegram.message;

import org.springframework.stereotype.Service;

@Service
public class HelpMessageProcessor implements MessageProcessor {
    private final String HELP_TEXT = "Для изменения времени оповещения до пары в минутах введите /delay новое_значение";

    @Override
    public String execute(String message, String chatId) {
        return HELP_TEXT;
    }
}
