package org.example.bookingbe.repository.RoomRepo;

import jakarta.persistence.LockModeType;
import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoomRepo extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r JOIN FETCH r.roomType WHERE r.hotel.id = :hotelId")
    List<Room> findRoomsByHotel(@Param("hotelId") Long hotelId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Room r WHERE r.id = :roomId AND r.status.id = 4")
    Optional<Room> findAvailableRoomForBooking(@Param("roomId") Long roomId);

    @Query("SELECT r FROM Room r WHERE r.status.id = 4")
    List<Room> findAvailableRooms();

}
