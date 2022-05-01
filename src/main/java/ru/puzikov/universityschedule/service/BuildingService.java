package ru.puzikov.universityschedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.model.Building;
import ru.puzikov.universityschedule.repo.BuildingRepository;
@Service
public class BuildingService {
    private final BuildingRepository repository;

    public BuildingService(BuildingRepository repository) {
        this.repository = repository;
    }

    public Building saveOrGet(Building building){
       return repository.findByLetter(building.getLetter()).orElse(repository.save(building));
    }
}
