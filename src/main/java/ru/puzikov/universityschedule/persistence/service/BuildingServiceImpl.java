package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Building;
import ru.puzikov.universityschedule.persistence.repo.BuildingRepository;

@Service
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository repository;

    public BuildingServiceImpl(BuildingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Building saveOrGet(Building building) {
        if (repository.existsByLetter(building.getLetter())) {
            return repository.findByLetter(building.getLetter()).get();
        }
        return repository.save(building);
    }
}
