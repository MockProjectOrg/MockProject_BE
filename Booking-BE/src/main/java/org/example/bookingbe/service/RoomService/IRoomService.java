package org.example.bookingbe.service.RoomService;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    List<Room> getRoomsByHotel(Long hotelId, Long userId);
    Room createRoom(Room room, Long userId);
    Room updateRoom(Long roomId, Room updatedRoom, Long userId);
    void deleteRoom(Long roomId, Long userId);

    List<Room> searchRooms(String hotelName, String typeName, Double minPrice, Double maxPrice, LocalDateTime checkIn, LocalDateTime checkOut);

    Optional<Room> getRoomById(Long roomId);

    Room updateRoomStatus(Long roomId, Long statusId, Long userId);

    List<Status> getAllStatuses();

    List<Room> getAvailableRooms();

}
