package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.Hotel;

import java.util.List;
import java.util.Optional;

import org.example.bookingbe.model.Hotel;

public interface IHotelService {
    Optional<Hotel> getHotelById(Long hotelId);
    List<Hotel> getAllHotels();
}
