package org.example.bookingbe.service.RoomService;


import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    Optional<Room> getRoomById(Long id);

    List<Room> getRoomsByHotel(Long hotelId, Long userId);

    Room createRoom(Room room, Long userId);

    Room updateRoom(Long roomId, Room updatedRoom, Long userId);

    void deleteRoom(Long roomId, Long userId);

    List<Room> searchRooms(String hotelName, String typeName, Double minPrice, Double maxPrice, LocalDateTime checkIn, LocalDateTime checkOut);

    Room updateRoomStatus(Long roomId, Long statusId, Long userId);

    List<Status> getAllStatuses();


    List<Room> getAvailableRooms();

    @Query("SELECT r FROM Room r JOIN FETCH r.roomType JOIN FETCH r.status JOIN FETCH r.hotel WHERE r.hotel.id = :hotelId AND r.status.id = 4")
    List<Room> getAvailableRoomsByHotel(@Param("hotelId") Long hotelId);

}
