package org.example.bookingbe.service.RoomService;

import org.springframework.stereotype.Service;
import org.example.bookingbe.repository.RoomRepo.RoomRepo;
@Service
public class RoomService implements IRoomService {
  private final RoomRepo roomRepo;
  public IRoomService(RoomRepo roomRepo){
    this.roomRepo = roomRepo;
  }
  @Override
  public Optional<Room> getRoomById(Long id){
    return roomRepo.findByid(id);
  }
}
