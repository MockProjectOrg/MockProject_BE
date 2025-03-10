package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.dto.HotelDTO;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService implements IHotelService {

    @Autowired
    private IHotelRepo hotelRepo;

    @Override
    public List<HotelDTO> findAllHotels() {
        List<Hotel> hotelList = hotelRepo.findAll();

        List<HotelDTO> hotelDTOList = new ArrayList<>();
        for (Hotel hotel : hotelList) {
            HotelDTO hotelDto = mapHotelToHotelDto(hotel);
            hotelDTOList.add(hotelDto);
        }
        return hotelDTOList;
    }

    private HotelDTO mapHotelToHotelDto(Hotel hotel) {
        return new HotelDTO(
                hotel.getId(),
                hotel.getHotelName(),
                hotel.getUser().getUserName()
        );
    }
}
