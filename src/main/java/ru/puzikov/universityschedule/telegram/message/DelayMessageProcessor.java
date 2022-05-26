package ru.puzikov.universityschedule.telegram.message;

import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.User;
import ru.puzikov.universityschedule.persistence.service.UserService;
import ru.puzikov.universityschedule.telegram.ScheduleBot;

@Service
public class DelayMessageProcessor implements MessageProcessor {
    final
    UserService userService;

    @Setter
    private ScheduleBot scheduleBot;

    public DelayMessageProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(String message, String chatId) {

        userService.changeDelay(chatId, Integer.parseInt(message));
        User user = userService.findByChatId(chatId);
        scheduleBot.changeNotifications(user);
        return "Задержка изменена";
    }
}
