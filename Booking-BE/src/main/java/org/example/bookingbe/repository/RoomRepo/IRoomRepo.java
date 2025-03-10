package org.example.bookingbe.repository.RoomRepo;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepo extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r JOIN FETCH r.roomType WHERE r.hotel.id = :hotelId")
    List<Room> findRoomsByHotel(@Param("hotelId") Long hotelId);


}
