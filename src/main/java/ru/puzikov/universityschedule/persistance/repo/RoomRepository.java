package ru.puzikov.universityschedule.persistance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.persistance.model.Building;
import ru.puzikov.universityschedule.persistance.model.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByNumberAndBuilding(String number, Building building);
}