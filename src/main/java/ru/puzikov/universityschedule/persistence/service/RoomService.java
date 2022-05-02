package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Room;
import ru.puzikov.universityschedule.persistence.repo.RoomRepository;

@Service
public class RoomService {
    final
    RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Room saveOrGet(Room room) {
        if (repository.findByNumberAndBuilding(room.getNumber(), room.getBuilding()).isEmpty())
            return repository.save(room);
        return repository
                .findByNumberAndBuilding(room.getNumber(),
                        room.getBuilding())
                .get();
    }
}
