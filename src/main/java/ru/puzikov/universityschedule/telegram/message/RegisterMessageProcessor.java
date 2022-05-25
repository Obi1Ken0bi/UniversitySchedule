package ru.puzikov.universityschedule.telegram.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Group;
import ru.puzikov.universityschedule.persistence.model.User;
import ru.puzikov.universityschedule.persistence.service.GroupService;
import ru.puzikov.universityschedule.persistence.service.UserService;

@Service
@Slf4j
public class RegisterMessageProcessor implements MessageProcessor {
    final
    GroupService groupService;

    final
    UserService userService;

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
        log.info(String.format("New user: %s", user));
        user.setGroup(group);

        return userService.saveOrEdit(user).toString();
    }
}
