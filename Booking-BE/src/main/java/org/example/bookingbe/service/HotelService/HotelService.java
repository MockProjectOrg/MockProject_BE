package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements IHotelService {
    @Autowired
    private IHotelRepo hotelRepo;

    @Override
    public Optional<Hotel> getHotelById(Long hotelId) {
        return hotelRepo.findById(hotelId);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }
}
