package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}