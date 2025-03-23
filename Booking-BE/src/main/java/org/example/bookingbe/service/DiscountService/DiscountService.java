package org.example.bookingbe.service.DiscountService;

import org.example.bookingbe.dto.DiscountDto;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.repository.DiscountRepo.IDiscountRepo;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.example.bookingbe.service.HotelService.IHotelService;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService implements IDiscountService {
    @Autowired
    private IDiscountRepo discountRepo;
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private IRoomRepo roomRepo;
    @Override
    public List<DiscountDto> getDiscounts(String userName, Long roomId) {
        Optional<Room> room = roomRepo.findById(roomId);
        return discountRepo.getAllDiscount(userName,room.get().getHotel().getId());
    }
}
