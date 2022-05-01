package ru.puzikov.universityschedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.puzikov.universityschedule.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}