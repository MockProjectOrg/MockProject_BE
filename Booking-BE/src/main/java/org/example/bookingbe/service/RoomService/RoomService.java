package org.example.bookingbe.service.RoomService;

import jakarta.transaction.Transactional;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService implements IRoomService {

    private final IRoomRepo roomRepo;

    public RoomService(IRoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Override
    @Transactional
    public Optional<Room> getRoomById(Long id) {
        return roomRepo.findById(id).map(room -> {
            // Force Hibernate to initialize lazy-loaded collections (like images)
            room.getImages().size();
            room.getReviews().size(); // Nếu bạn cần các review nữa
            return room;
        });
    }

    @Override
    public Room findById(Long id) {
        return roomRepo.findById(id).orElse(null);
    }
}