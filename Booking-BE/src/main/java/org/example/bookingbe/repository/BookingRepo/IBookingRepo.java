package org.example.bookingbe.repository.BookingRepo;

import org.example.bookingbe.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingRepo extends JpaRepository<Booking, Long> {
}
