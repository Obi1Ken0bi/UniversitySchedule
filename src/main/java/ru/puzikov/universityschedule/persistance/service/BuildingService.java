package ru.puzikov.universityschedule.persistance.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistance.model.Building;
import ru.puzikov.universityschedule.persistance.repo.BuildingRepository;

@Service
public class BuildingService {
    private final BuildingRepository repository;

    public BuildingService(BuildingRepository repository) {
        this.repository = repository;
    }

    public Building saveOrGet(Building building) {
        if (repository.existsByLetter(building.getLetter())) {
            return repository.findByLetter(building.getLetter()).get();
        }
        return repository.save(building);
    }
}
