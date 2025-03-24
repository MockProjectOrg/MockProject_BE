package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.Hotel;

import java.util.List;
import java.util.Optional;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;

public interface IHotelService {
    Optional<Hotel> getHotelById(Long hotelId);
    List<Hotel> getAllHotels();
    Hotel findById(Long id);
    User getUserByUsername(String username);
}
