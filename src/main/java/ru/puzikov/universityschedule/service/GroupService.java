package ru.puzikov.universityschedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.model.Group;
import ru.puzikov.universityschedule.repo.GroupRepository;

@Service
public class GroupService {
    final
    GroupRepository repository;

    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public Group saveOrGet(Group group){
       return repository.findByNumber(group.getNumber())
                .orElse(repository.save(group));
    }
}
