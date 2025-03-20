package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.Hotel;

public interface IHotelService {
    Hotel findById(Long id);

    Object getUserByUsername(String username);
}
