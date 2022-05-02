package ru.puzikov.universityschedule.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.dto.PairDto;
import ru.puzikov.universityschedule.exception.GroupNotFoundException;
import ru.puzikov.universityschedule.persistence.model.*;
import ru.puzikov.universityschedule.persistence.repo.GroupRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class GroupService {
    final
    GroupRepository repository;


    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public Group saveOrGet(Group group) {
        if (repository.findByNumber(group.getNumber()).isEmpty()) {
            return repository.save(group);
        }
        return repository.findByNumber(group.getNumber()).get();
    }

    public PairDto getNextPair(Group group) throws GroupNotFoundException {
        Pair pairToReturn = null;
        Group group1 = repository.findByNumber(group.getNumber()).orElseThrow(GroupNotFoundException::new);
        LocalTime time = LocalTime.now();
        List<Day> days = group1.getSchedule().getDays();
        int i = LocalDate.now().getDayOfWeek().getValue() - 1;
        if (i == 6) {
            return new PairDto(null, null);
        }
        DayOfWeek dayOfWeek = DayOfWeek.getDayOfWeek(i);
        for (int j = 0; j < days.size(); j++) {
            Day day = days.get(i);
            if (day.getDayOfWeek().equals(dayOfWeek)) {
                List<Pair> pairs = day.getPairs();
                int min = Integer.MAX_VALUE;
                for (Pair pair : pairs) {
                    int range = pair.getTime().toSecondOfDay() - time.toSecondOfDay();
                    if (range < min && range > 0) {
                        min = range;
                        pairToReturn = pair;
                    }
                }
            }
        }
        if (pairToReturn == null)
            return new PairDto(null, null);
        LocalDateTime upperWeekDate = LocalDateTime.of(2022, 5, 2, 0, 5);
        LocalDateTime now = LocalDateTime.now();
        Lesson lesson;
        if (!pairToReturn.isWeekDependent()) {
            lesson = pairToReturn.getUpperLesson();
        } else if ((Duration.between(upperWeekDate, now).toDays() / 7) % 2 == 0)
            lesson = pairToReturn.getUpperLesson();
        else
            lesson = pairToReturn.getDownLesson();

        return new PairDto(lesson, pairToReturn.getTime());

    }

    public void deleteAll() {
        repository.deleteAll();
    }


}
