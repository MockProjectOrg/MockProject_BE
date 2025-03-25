package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements IHotelService {
    @Autowired
    private IHotelRepo hotelRepo;
    @Autowired
    private IUserRepo userRepo;

    @Override
    public Optional<Hotel> getHotelById(Long hotelId) {
        return hotelRepo.findById(hotelId);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    public List<Hotel> getHotelByUserId(Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        return hotelRepo.findByUser(user);
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepo.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }
}
