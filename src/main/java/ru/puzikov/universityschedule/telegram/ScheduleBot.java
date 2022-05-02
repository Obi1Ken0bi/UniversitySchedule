package ru.puzikov.universityschedule.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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

import java.time.LocalTime;

@Component
@Slf4j
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
    public void sendMessage(String chatId,String message){
        SendMessage sendMessage=new SendMessage();
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
        if(update.hasMessage() && update.getMessage().hasText()) {
            String groupId = update.getMessage().getText().trim();
            String chatId = update.getMessage().getChatId().toString();

            Group group=groupService.findByNumber(Integer.parseInt(groupId));
            User user=new User();
            user.setGroup(group);
            user.setChatId(chatId);
            SendMessage sm = new SendMessage();
            user=userService.saveOrEdit(user);
            sm.setChatId(chatId);
            sm.setText(user.toString());

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }
    @Scheduled(fixedRate = 600000)
    public void notifyUsers(){
        userService.findAll().forEach(x-> {
            try {

                PairDto nextPair = groupService.getNextPair(x.getGroup());
                if(nextPair.getLesson()!=null) {
                    if (nextPair.getTime().toSecondOfDay()- LocalTime.now().toSecondOfDay()<600) {
                        sendMessage(x.getChatId(), nextPair.toString());
                        log.info("Message sent");
                    }
                }

            } catch (GroupNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
