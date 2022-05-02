package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.User;

import java.util.List;

public interface UserService {
    public User findByChatId(String chatId);

    public List<User> findAll();

    public User saveOrEdit(User user);
}
