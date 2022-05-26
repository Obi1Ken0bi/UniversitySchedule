package ru.puzikov.universityschedule.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.puzikov.universityschedule.dto.PairDto;
import ru.puzikov.universityschedule.misc.TelegramPropertiesReader;
import ru.puzikov.universityschedule.persistence.model.Pair;
import ru.puzikov.universityschedule.persistence.model.User;
import ru.puzikov.universityschedule.persistence.service.GroupService;
import ru.puzikov.universityschedule.persistence.service.UserServiceImpl;
import ru.puzikov.universityschedule.telegram.message.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Component
public class ScheduleBot extends TelegramLongPollingBot {
    final
    UserServiceImpl userService;

    private final ExecutorService service = Executors.newFixedThreadPool(5);

    private final Map<User, List<CompletableFuture>> futureMap = new HashMap<>();

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
        delayMessageProcessor.setScheduleBot(this);
        registerMessageProcessor.setScheduleBot(this);
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


    public void notifyUsers() {
        userService.findAll().forEach(user -> {
            queueNotifications(user);
            log.info("scheduling worked");


        });
    }

    public void changeDelay(User user) {

        var completableFutures = futureMap.get(user);
        if(completableFutures!=null) {
            for (var future : completableFutures) {
                future.cancel(true);
            }
        }
        queueNotifications(user);
    }

    @Transactional
    public void queueNotifications(User user) {
        log.info(String.valueOf(user));
        Stream<Pair> pairStream = groupService.getPairsForDay(user.getGroup().getNumber(), LocalDate.now().getDayOfWeek().getValue())
                .getPairs()
                .stream()
                .sorted(Comparator.comparingInt(d -> d.getTime().getHour()));
        List<Pair> pairs = pairStream.collect(Collectors.toList());
        for (Pair pair : pairs) {
            PairDto pairDto = new PairDto(pair.getLesson(), pair.getTime());
            int delay = pairDto.minutesToPair() - user.getDelay();
            if (delay < 0 && pairDto.minutesToPair() < 0)
                continue;
            delay = Math.max(delay, 0);
            Executor afterDelay = CompletableFuture.delayedExecutor(delay, TimeUnit.MINUTES, service);
            CompletableFuture<Void> completableFuture =
                    CompletableFuture.runAsync(() -> sendMessage(user.getChatId(), pairDto.toString()), afterDelay);
            var futureList = futureMap.getOrDefault(user, new ArrayList<>());
            futureList.add(completableFuture);
            futureMap.put(user, futureList);
            completableFuture.thenRun(() -> log.info("message sent"));
        }
    }

}
