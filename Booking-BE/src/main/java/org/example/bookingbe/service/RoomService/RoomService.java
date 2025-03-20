package org.example.bookingbe.service.RoomService;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements IRoomService {

    private final IRoomRepo roomRepo;

    public RoomService(IRoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found with id: " + id));
    }


    @Override
    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepo.findByHotel_Id(hotelId);
    }
}