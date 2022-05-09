package ru.puzikov.universityschedule.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.dto.PairDto;
import ru.puzikov.universityschedule.exception.GroupNotFoundException;
import ru.puzikov.universityschedule.persistence.model.*;
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
    public PairDto getNextPair(Group group) throws GroupNotFoundException {
        Pair pairToReturn = null;
        int dayInWeek = LocalDate.now().getDayOfWeek().getValue() - 1;
        if (dayInWeek == 6) {
            return new PairDto(null, null);
        }
        DayOfWeek dayOfWeek = DayOfWeek.getDayOfWeek(dayInWeek);
        Group group1 = findGroup(group);
        List<Day> days = group1.getSchedule().getDays();
        LocalTime time = LocalTime.now();

        for (Day day : days) {
            if (day.getDayOfWeek().equals(dayOfWeek)) {
                List<Pair> pairs = day.getPairs();
                int min = Integer.MAX_VALUE;
                for (Pair pair : pairs) {
                    int range = pair.getRange(time);
                    if (range < min && range > 0) {
                        min = range;
                        pairToReturn = pair;
                    }
                }
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
