package org.example.bookingbe.service.RoomService;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IRoomRepo roomRepo;

    @Override
    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    @Override
    public Optional<Room> findById(Long id) {
        return roomRepo.findById(id);
    }

    @Override
    public Room save(Room room) {
        return roomRepo.save(room);
    }

    @Override
    public void deleteById(Long id) {
        roomRepo.deleteById(id);
    }

    @Override
    public List<Room> searchRooms(Double minPrice, Double maxPrice, String description) { // CẬP NHẬT
        if (minPrice != null && maxPrice != null && description != null && !description.trim().isEmpty()) {
            return roomRepo.findByPriceBetweenAndDescriptionContainingIgnoreCase(minPrice, maxPrice, description);
        } else if (minPrice != null && maxPrice != null) {
            return roomRepo.findByPriceBetween(minPrice, maxPrice);
        } else {
            return roomRepo.findByDescriptionContainingIgnoreCase(description);
        }
    }
}
