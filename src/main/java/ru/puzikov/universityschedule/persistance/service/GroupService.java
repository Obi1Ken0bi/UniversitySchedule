package ru.puzikov.universityschedule.persistance.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistance.model.Day;
import ru.puzikov.universityschedule.persistance.model.DayOfWeek;
import ru.puzikov.universityschedule.persistance.model.Group;
import ru.puzikov.universityschedule.persistance.model.Pair;
import ru.puzikov.universityschedule.persistance.repo.GroupRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
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

    public Pair getNextPair(Group group){
        Pair pairToReturn=null;
        Group group1 = repository.findByNumber(group.getNumber()).get();
        LocalTime time=LocalTime.now();
        List<Day> days = group1.getSchedule().getDays();
        int i = LocalDate.now().getDayOfWeek().getValue() - 1;
        i=0;
        DayOfWeek dayOfWeek = DayOfWeek.getDayOfWeek(i);
        for (int j = 0; j < days.size(); j++) {
            Day day = days.get(i);
            if(day.getDayOfWeek().equals(dayOfWeek)){
                List<Pair> pairs = day.getPairs();
                int min=Integer.MAX_VALUE;
                for (Pair pair : pairs) {
                    int range = pair.getTime().toSecondOfDay() - time.toSecondOfDay();
                    if (range < min) {
                        min = range;
                        pairToReturn = pair;
                    }
                }
            }
        }
        return pairToReturn;

    }
}
