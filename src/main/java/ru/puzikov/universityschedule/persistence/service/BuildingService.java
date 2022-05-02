package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Building;
import ru.puzikov.universityschedule.persistence.repo.BuildingRepository;

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
