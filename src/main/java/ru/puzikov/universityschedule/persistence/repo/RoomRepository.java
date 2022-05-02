package ru.puzikov.universityschedule.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistence.model.Building;
import ru.puzikov.universityschedule.persistence.model.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByNumberAndBuilding(String number, Building building);
}