package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.dto.HotelDTO;

import java.util.List;

public interface IHotelService {

    HotelDTO createHotel(HotelDTO hotelDTO);
    List<HotelDTO> findAllHotels();
    HotelDTO findHotelById(Long id);
    HotelDTO updateHotel(Long id, HotelDTO hotelDTO);
    void deleteHotel(Long id);
}
