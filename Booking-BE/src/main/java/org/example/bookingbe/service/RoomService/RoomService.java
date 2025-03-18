package org.example.bookingbe.service.RoomService;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private final IRoomRepo roomRepo;

    public RoomService(IRoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepo.findByHotelId(hotelId);
    }

}
