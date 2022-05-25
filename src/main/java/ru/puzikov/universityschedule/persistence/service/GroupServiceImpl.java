package ru.puzikov.universityschedule.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.dto.PairDto;
import ru.puzikov.universityschedule.exception.GroupNotFoundException;
import ru.puzikov.universityschedule.persistence.model.Day;
import ru.puzikov.universityschedule.persistence.model.Group;
import ru.puzikov.universityschedule.persistence.model.Lesson;
import ru.puzikov.universityschedule.persistence.model.Pair;
import ru.puzikov.universityschedule.persistence.repo.GroupRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {
    final
    GroupRepository repository;


    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public Group saveOrGet(Group group) {
        if (repository.findByNumber(group.getNumber()).isEmpty()) {
            return repository.save(group);
        }
        return repository.findByNumber(group.getNumber()).get();
    }

    @Override
    @Transactional

    public Group findByNumber(Integer number) {
        return repository.findByNumber(number).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public Day getPairsForDay(int groupNumber, int k) {
        return repository.findByNumber(groupNumber)
                .get()
                .getSchedule()
                .get(k);
    }

    @Override
    public PairDto getNextPair(Group group) throws GroupNotFoundException {
        Pair pairToReturn = null;
        int dayInWeek = LocalDate.now().getDayOfWeek().getValue();
        if (dayInWeek == 7) {
            return new PairDto(null, null);
        }
        Group group1 = findGroup(group);
        Day day = group1.getSchedule().get(dayInWeek);
        LocalTime time = LocalTime.now();


        List<Pair> pairs = day.getPairs();
        int min = Integer.MAX_VALUE;
        for (Pair pair : pairs) {
            int range = pair.getRange(time);
            if (range < min && range > 0) {
                min = range;
                pairToReturn = pair;
            }
        }
        if (pairToReturn == null)
            return new PairDto(null, null);
        Lesson lesson = pairToReturn.getLesson();

        return new PairDto(lesson, pairToReturn.getTime());

    }

    private Group findGroup(Group group) throws GroupNotFoundException {
        return repository.findByNumber(group.getNumber()).orElseThrow(GroupNotFoundException::new);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }


}
