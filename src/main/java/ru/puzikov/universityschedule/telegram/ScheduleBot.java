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
import ru.puzikov.universityschedule.persistence.service.GroupService;
import ru.puzikov.universityschedule.persistence.service.UserServiceImpl;
import ru.puzikov.universityschedule.telegram.message.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Component
public class ScheduleBot extends TelegramLongPollingBot {
    final
    UserServiceImpl userService;

    private final ScheduledExecutorService service = new ScheduledThreadPoolExecutor(5);

    private final GroupService groupService;
    private final RegisterMessageProcessor registerMessageProcessor;

    private final Map<String, MessageProcessor> messageMap = new HashMap<>();

    private final String name;
    private final String token;

    public ScheduleBot(TelegramPropertiesReader telegramPropertiesReader,
                       UserServiceImpl userService,
                       GroupService groupService,
                       RegisterMessageProcessor registerMessageProcessor,
                       DelayMessageProcessor delayMessageProcessor,
                       HelpMessageProcessor helpMessageProcessor,
                       StartMessageProcessor startMessageProcessor) {
        token = telegramPropertiesReader.getProperty("telegram.token");
        name = telegramPropertiesReader.getProperty("telegram.name");
        this.userService = userService;
        this.groupService = groupService;
        this.registerMessageProcessor = registerMessageProcessor;
        messageMap.put("/help", helpMessageProcessor);
        messageMap.put("/start", startMessageProcessor);
        messageMap.put("/delay", delayMessageProcessor);
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
            String message = getCleanText(update);
            String command = findCommand(message);
            String chatId = getChatId(update);
            MessageProcessor messageProcessor = messageMap.getOrDefault(command.trim(), registerMessageProcessor);
            String answer = messageProcessor.execute(message.replace(command, "").trim(), chatId);
            SendMessage sm = new SendMessage(chatId, answer);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }

    private String findCommand(String message) {
        Pattern pattern = Pattern.compile("/[a-z]*");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find())
            return matcher.group();
        return "";
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

                    int delay = nextPair.minutesToPair() - x.getDelay();
                    service.schedule(() -> sendMessage(x.getChatId(), nextPair.toString()),
                            Math.max(delay, 0),
                            TimeUnit.MINUTES);


                    log.info("Message sent");

                }
                log.info("scheduling worked");

            } catch (GroupNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
