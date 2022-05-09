package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.dto.PairDto;
import ru.puzikov.universityschedule.exception.GroupNotFoundException;
import ru.puzikov.universityschedule.persistence.model.Group;

import javax.transaction.Transactional;

public interface GroupService {
    Group saveOrGet(Group group);

    @Transactional
    Group findByNumber(Integer number);

    PairDto getNextPair(Group group) throws GroupNotFoundException;

    void deleteAll();
}
