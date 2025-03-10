package org.example.bookingbe.service.RoomService;

import org.example.bookingbe.model.Room;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    List<Room> findAll();
    Optional<Room> findById(Long id);
    Room save(Room room);
    void deleteById(Long id);
    List<Room> searchRooms(Double minPrice, Double maxPrice, String description); // CẬP NHẬT
}
