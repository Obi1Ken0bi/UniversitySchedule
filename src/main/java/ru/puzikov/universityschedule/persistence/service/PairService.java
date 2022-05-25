package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.Pair;

import java.time.LocalTime;
import java.util.List;

public interface PairService {
    Pair saveOrGet(Pair pair);

    List<LocalTime> getDistinctTimes();
}
