package org.example.bookingbe.repository.HotelRepo;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IHotelRepo extends JpaRepository<Hotel, Long> {
    List<Hotel> findByUser(User user);
    List<Hotel> findByUserId(Long userId);

    Optional<Hotel> findById(Long hotelId);
    List<Hotel> findByHotelNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String hotelName, String address);
}
