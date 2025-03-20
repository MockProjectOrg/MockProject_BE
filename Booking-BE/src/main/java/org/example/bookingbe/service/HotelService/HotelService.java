package org.example.bookingbe.service.HotelService;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;
import org.example.bookingbe.model.dto.HotelDTO;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService implements IHotelService {

    @Autowired
    private IHotelRepo hotelRepo;

    @Autowired
    private IUserRepo userRepo;

    private HotelDTO convertToDTO(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setId(hotel.getId());
        dto.setHotelName(hotel.getHotelName());
        dto.setAddress(hotel.getAddress());
        dto.setDescription(hotel.getDescription());
        return dto;
    }

    @Override
    public HotelDTO createHotel(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelDTO.getHotelName());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setDescription(hotelDTO.getDescription());
        User user = userRepo.findById(hotelDTO.getUserId()).orElseThrow();
        hotel.setUser(user);
        return convertToDTO(hotelRepo.save(hotel));
    }

    @Override
    public List<HotelDTO> findAllHotels() {
        return hotelRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public HotelDTO findHotelById(Long id) {
        return convertToDTO(hotelRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid hotel ID")
        ));
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO hotelDTO) {
        Hotel hotel = hotelRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid hotel ID")
        );
        hotel.setHotelName(hotelDTO.getHotelName());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setDescription(hotelDTO.getDescription());
        return convertToDTO(hotelRepo.save(hotel));
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepo.deleteById(id);
    }
}
