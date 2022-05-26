package ru.puzikov.universityschedule.telegram.message;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Group;
import ru.puzikov.universityschedule.persistence.model.User;
import ru.puzikov.universityschedule.persistence.service.GroupService;
import ru.puzikov.universityschedule.persistence.service.UserService;
import ru.puzikov.universityschedule.telegram.ScheduleBot;

@Service
@Slf4j
public class RegisterMessageProcessor implements MessageProcessor {
    final
    GroupService groupService;
    final
    UserService userService;
    @Setter
    private ScheduleBot scheduleBot;

    public RegisterMessageProcessor(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;

    }

    @Override
    public String execute(String message, String chatId) {
        return registerUser(message, chatId);
    }

    private String registerUser(String groupId, String chatId) {
        Group group = groupService.findByNumber(Integer.parseInt(groupId));


        User user = userService.findByChatId(chatId);
        Group oldGroup = user.getGroup();
        if (oldGroup == null) {
            user.setGroup(group);
            scheduleBot.queueNotifications(user);
        } else if (oldGroup != group) {
            user.setGroup(group);
            scheduleBot.changeNotifications(user);
        } else
            user.setGroup(group);
        log.info(String.format("New user: %s", user));

        User user1 = userService.saveOrEdit(user);
        return "Вы успешно зарегистрировались как студент группы " + user1.getGroup().getNumber();
    }
}
