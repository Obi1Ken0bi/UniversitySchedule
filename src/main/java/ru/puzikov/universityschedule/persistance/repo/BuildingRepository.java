package ru.puzikov.universityschedule.persistance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistance.model.Building;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByLetter(Character letter);

    boolean existsByLetter(Character letter);

}