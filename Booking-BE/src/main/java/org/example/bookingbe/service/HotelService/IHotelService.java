package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.dto.HotelDTO;

import java.util.List;

public interface IHotelService {

    List<HotelDTO> findAllHotels();
}
