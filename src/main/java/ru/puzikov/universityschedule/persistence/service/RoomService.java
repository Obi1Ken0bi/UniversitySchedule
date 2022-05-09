package ru.puzikov.universityschedule.persistence.service;

import ru.puzikov.universityschedule.persistence.model.Room;

public interface RoomService {
    Room saveOrGet(Room room);
}
