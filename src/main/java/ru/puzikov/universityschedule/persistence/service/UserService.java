package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.User;

import java.util.List;

public interface UserService {
    User findByChatId(String chatId);

    List<User> findAll();

    User saveOrEdit(User user);
}
