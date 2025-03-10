package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService implements IHotelService {

    private final IHotelRepo hotelRepo;

    public HotelService(IHotelRepo hotelRepo) {
        this.hotelRepo = hotelRepo;
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    @Override
    public void saveHotel(Hotel hotel) {
        hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> searchHotels(String keyword) {
        return hotelRepo.findByHotelNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword);
    }
}

