package org.example.bookingbe.service;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepo roomRepository;

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }
}
