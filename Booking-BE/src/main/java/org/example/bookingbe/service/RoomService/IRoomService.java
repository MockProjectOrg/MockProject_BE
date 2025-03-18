package org.example.bookingbe.service.RoomService;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.Status;

import java.util.List;
import java.util.Optional;

public interface IRoomService {
    List<Room> getRoomsByHotel(Long hotelId, Long userId);
    Room createRoom(Room room, Long userId);
    Room updateRoom(Long roomId, Room updatedRoom, Long userId);
    void deleteRoom(Long roomId, Long userId);

    Optional<Room> getRoomById(Long roomId);

    Room updateRoomStatus(Long roomId, Long statusId, Long userId);

    List<Status> getAllStatuses();

}
