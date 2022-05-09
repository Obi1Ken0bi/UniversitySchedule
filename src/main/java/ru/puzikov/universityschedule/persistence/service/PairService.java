package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.Pair;

public interface PairService {
    Pair saveOrGet(Pair pair);
}
