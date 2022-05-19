package ru.puzikov.universityschedule.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.puzikov.universityschedule.dto.PairDto;
import ru.puzikov.universityschedule.exception.GroupNotFoundException;
import ru.puzikov.universityschedule.misc.TelegramPropertiesReader;
import ru.puzikov.universityschedule.persistence.model.Group;
import ru.puzikov.universityschedule.persistence.model.User;
import ru.puzikov.universityschedule.persistence.service.GroupService;
import ru.puzikov.universityschedule.persistence.service.UserServiceImpl;

import javax.transaction.Transactional;


@Slf4j
@Component
public class ScheduleBot extends TelegramLongPollingBot {
    final
    UserServiceImpl userService;

    private final GroupService groupService;


    private final String name;
    private final String token;

    public ScheduleBot(TelegramPropertiesReader telegramPropertiesReader, UserServiceImpl userService, GroupService groupService) {
        token = telegramPropertiesReader.getProperty("telegram.token");
        name = telegramPropertiesReader.getProperty("telegram.name");
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String groupId = getCleanText(update);
            String chatId = getChatId(update);
            if (groupId.equals("/start"))
                return;
            SendMessage sm = registerUser(groupId, chatId);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }

    private SendMessage registerUser(String groupId, String chatId) {
        Group group = groupService.findByNumber(Integer.parseInt(groupId));
        User user = new User();
        user.setGroup(group);
        user.setChatId(chatId);
        user = userService.saveOrEdit(user);
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(user.toString());
        return sm;
    }

    private String getChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    private String getCleanText(Update update) {
        return update.getMessage().getText().trim();
    }

    @Transactional
    public void notifyUsers() {
        userService.findAll().forEach(x -> {
            try {

                PairDto nextPair = groupService.getNextPair(x.getGroup());
                if (nextPair.getLesson() != null) {
                    if (nextPair.isTimeToNotify()) {
                        sendMessage(x.getChatId(), nextPair.toString());
                        log.info("Message sent");
                    }
                }
                log.info("scheduling worked");

            } catch (GroupNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
