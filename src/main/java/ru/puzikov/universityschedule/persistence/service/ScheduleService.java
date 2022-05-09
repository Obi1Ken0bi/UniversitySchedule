package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.Schedule;

public interface ScheduleService {
    Schedule save(Schedule schedule);
}
