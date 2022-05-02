package ru.puzikov.universityschedule.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistence.model.Building;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByLetter(Character letter);

    boolean existsByLetter(Character letter);

}