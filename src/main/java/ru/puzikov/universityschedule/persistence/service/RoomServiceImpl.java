package ru.puzikov.universityschedule.persistence.service;

import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.persistence.model.Room;
import ru.puzikov.universityschedule.persistence.repo.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {
    final
    RoomRepository repository;

    public RoomServiceImpl(RoomRepository repository) {
        this.repository = repository;
    }

    @Override
    public Room saveOrGet(Room room) {
        if (repository.findByNumberAndBuilding(room.getNumber(), room.getBuilding()).isEmpty())
            return repository.save(room);
        return repository
                .findByNumberAndBuilding(room.getNumber(),
                        room.getBuilding())
                .get();
    }
}
