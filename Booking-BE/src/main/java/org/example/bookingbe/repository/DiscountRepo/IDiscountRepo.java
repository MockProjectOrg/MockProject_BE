package org.example.bookingbe.repository.DiscountRepo;

import org.example.bookingbe.model.Discount;
import org.example.bookingbe.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDiscountRepo extends JpaRepository<Discount, Long> {
    List<Discount> findByHotel(Hotel hotel);
    List<Discount> findByHotelAndIsActiveTrue(Hotel hotel);
}
