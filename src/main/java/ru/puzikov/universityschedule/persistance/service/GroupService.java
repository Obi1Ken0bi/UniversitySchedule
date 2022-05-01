package ru.puzikov.universityschedule.persistance.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistance.model.Group;
import ru.puzikov.universityschedule.persistance.repo.GroupRepository;

@Service
public class GroupService {
    final
    GroupRepository repository;

    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public Group saveOrGet(Group group) {
        if(repository.findByNumber(group.getNumber()).isEmpty()){
            return repository.save(group);
        }
        return repository.findByNumber(group.getNumber()).get();
    }
}
