package ru.puzikov.universityschedule.persistance.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistance.model.Room;
import ru.puzikov.universityschedule.persistance.repo.RoomRepository;

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
