package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.User;
import ru.puzikov.universityschedule.persistence.repo.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    final
    UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findByChatId(String chatId) {
        return repository.findByChatId(chatId).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User saveOrEdit(User user) {
        if(repository.findByChatId(user.getChatId()).isEmpty())
            return repository.save(user);
        User user1 = repository.findByChatId(user.getChatId()).get();
        user1.setGroup(user.getGroup());
        return repository.save(user1);
    }
}
