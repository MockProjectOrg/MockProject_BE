package org.example.bookingbe.service.RoomService;

import org.example.bookingbe.model.Room;

import java.util.List;
import java.util.Optional;

public interface IRoomService {
    List<Room> getRoomsByHotel(Long hotelId, Long userId);
    Room createRoom(Room room, Long userId);
    Room updateRoom(Long roomId, Room updatedRoom, Long userId);
    void deleteRoom(Long roomId, Long userId);

    List<Room> searchRooms(Long hotelId, Long roomTypeId, Double minPrice, Double maxPrice);

    Optional<Room> getRoomById(Long roomId);
}
