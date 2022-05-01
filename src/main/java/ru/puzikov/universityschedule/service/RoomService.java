package ru.puzikov.universityschedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.puzikov.universityschedule.model.Room;
import ru.puzikov.universityschedule.repo.RoomRepository;

@Service
public class RoomService {
    final
    RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Room saveOrGet(Room room){
        return repository.findByNumberAndBuilding(room.getNumber(),room.getBuilding()).orElse(repository.save(room));
    }
}
