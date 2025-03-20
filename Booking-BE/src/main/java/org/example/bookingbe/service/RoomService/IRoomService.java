package org.example.bookingbe.service.RoomService;


import org.example.bookingbe.model.Room;

import java.util.Optional;

public interface IRoomService {
    Optional<Room> getRoomById(Long id);

    Room findById(Long id);
}
