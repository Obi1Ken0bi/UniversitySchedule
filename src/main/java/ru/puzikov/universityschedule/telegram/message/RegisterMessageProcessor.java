package ru.puzikov.universityschedule.telegram.message;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.exception.GroupNotFoundException;
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
        Group group;
        try {
            User user = userService.findByChatId(chatId);
            int numberOfGroupFromInput = Integer.parseInt(groupId);




        Group oldGroup = user.getGroup();
        if (oldGroup == null) {
            group = groupService.findByNumber(numberOfGroupFromInput);
            user.setGroup(group);
            user = userService.saveOrEdit(user);
            scheduleBot.queueNotifications(user);
            log.info(String.format("new user: %s", user));

        } else if (oldGroup.getNumber() != numberOfGroupFromInput) {
            group = groupService.findByNumber(numberOfGroupFromInput);
            user.setGroup(group);
            user = userService.saveOrEdit(user);
            scheduleBot.changeNotifications(user);
            log.info(String.format("user changed group: %s", user));
        }else
        log.info(String.format("user not changed: %s", user));

        return "Вы успешно зарегистрировались как студент группы " + user.getGroup().getNumber();
        } catch (GroupNotFoundException e) {
            return "Группа не найдена";
        }
    }
}
