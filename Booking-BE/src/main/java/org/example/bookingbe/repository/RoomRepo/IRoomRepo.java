package org.example.bookingbe.repository.RoomRepo;

import org.example.bookingbe.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepo extends JpaRepository<Room, Long> {
    List<Room> findByHotel_Id(Long hotelId);
}
