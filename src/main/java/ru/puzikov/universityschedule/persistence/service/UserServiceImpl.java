package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.User;
import ru.puzikov.universityschedule.persistence.repo.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    final
    UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }




    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User saveOrEdit(User user) {
        if (repository.findByChatId(user.getChatId()).isEmpty())
            return repository.save(user);
        User user1 = repository.findByChatId(user.getChatId()).get();
        user1.setGroup(user.getGroup());
        user1.setDelay(user.getDelay());
        return repository.save(user1);
    }

    @Override
    public User findByChatIdOrSave(User user) {
        if (repository.findByChatId(user.getChatId()).isEmpty())
            return repository.save(user);
        return repository.findByChatId(user.getChatId()).get();
    }

    public User findByChatId(String chatId) {
        return repository.findByChatId(chatId).orElse(new User(chatId));
    }

    @Override
    public void changeDelay(String chatId, int delay) {
        User user = findByChatId(chatId);
        user.setDelay(delay);
        saveOrEdit(user);
    }
}
