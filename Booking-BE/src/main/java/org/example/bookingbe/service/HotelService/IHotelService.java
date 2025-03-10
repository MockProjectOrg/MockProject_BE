package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.Hotel;
import java.util.List;

public interface IHotelService {
    List<Hotel> getAllHotels();
    void saveHotel(Hotel hotel);
    List<Hotel> searchHotels(String keyword); // New method for searching
}
