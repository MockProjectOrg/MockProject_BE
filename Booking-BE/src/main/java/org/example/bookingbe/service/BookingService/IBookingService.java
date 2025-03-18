package org.example.bookingbe.service.BookingService;

import org.example.bookingbe.model.Booking;
import org.example.bookingbe.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface IBookingService {
    List<Booking> getAllBookings();
    Optional<Booking> getBookingById(Long id);
    Booking saveBooking(Booking booking);
    void deleteBooking(Long id);
    List<Booking> getBookingsByUserId(Long userId);
    List<Booking> getBookingsByHotelManager(Long managerId);

    // Thêm các phương thức mới
    List<Booking> getBookingsByHotelId(Long hotelId);
    boolean isBookingBelongToHotel(Long bookingId, Long hotelId);
}
