package org.example.bookingbe.repository.HotelRepo;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelRepo extends JpaRepository<Hotel, Long> {
    Hotel findByUser(User user);
}
