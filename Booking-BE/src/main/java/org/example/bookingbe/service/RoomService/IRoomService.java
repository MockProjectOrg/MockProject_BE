package org.example.bookingbe.service.RoomService;

import org.example.bookingbe.model.Room;

import java.util.List;

public interface IRoomService {
    List<Room> getRoomsByHotel(Long hotelId, Long userId);
    Room createRoom(Room room, Long userId);
    Room updateRoom(Long roomId, Room updatedRoom, Long userId);
    void deleteRoom(Long roomId, Long userId);
}
