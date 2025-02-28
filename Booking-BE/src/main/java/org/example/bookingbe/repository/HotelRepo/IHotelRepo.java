package org.example.bookingbe.repository.HotelRepo;

import org.example.bookingbe.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelRepo extends JpaRepository<Hotel, Long> {
}
