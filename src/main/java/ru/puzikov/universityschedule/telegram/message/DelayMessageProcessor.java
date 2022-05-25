package ru.puzikov.universityschedule.telegram.message;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.service.UserService;

@Service
public class DelayMessageProcessor implements MessageProcessor {
    final
    UserService userService;

    public DelayMessageProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(String message, String chatId) {

        userService.changeDelay(chatId, Integer.parseInt(message));
        return "Задержка изменена";
    }
}
