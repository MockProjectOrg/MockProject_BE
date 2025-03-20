package org.example.bookingbe.service.RoomService;


import org.example.bookingbe.model.Room;

import java.util.List;

public interface IRoomService {
    Room getRoomById(Long id);

    List<Room> getRoomsByHotel(Long hotelId);
}
