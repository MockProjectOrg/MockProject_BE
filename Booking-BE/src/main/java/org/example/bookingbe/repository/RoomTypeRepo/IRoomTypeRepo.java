package org.example.bookingbe.repository.RoomTypeRepo;

import org.example.bookingbe.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomTypeRepo extends JpaRepository<RoomType, Long> {
}
