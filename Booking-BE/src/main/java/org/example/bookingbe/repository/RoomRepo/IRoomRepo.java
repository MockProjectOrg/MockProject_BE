package org.example.bookingbe.repository.RoomRepo;

import org.example.bookingbe.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IRoomRepo extends JpaRepository<Room, Long> {
    List<Room> findByPriceBetween(Double minPrice, Double maxPrice); // CẬP NHẬT
    List<Room> findByPriceBetweenAndDescriptionContainingIgnoreCase(Double minPrice, Double maxPrice, String description); // CẬP NHẬT
    List<Room> findByDescriptionContainingIgnoreCase(String description);
}
