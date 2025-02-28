package org.example.bookingbe.repository.RoomRepo;

import org.example.bookingbe.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepo extends JpaRepository<Room, Long> {
}
