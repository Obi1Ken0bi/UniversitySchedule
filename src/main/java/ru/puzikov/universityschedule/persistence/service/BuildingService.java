package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.Building;

public interface BuildingService {
    Building saveOrGet(Building building);
}
