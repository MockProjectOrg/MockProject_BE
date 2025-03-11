package org.example.bookingbe.service.BookingService;

import org.example.bookingbe.model.Booking;
import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepo bookingRepo;

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepo.findById(id);
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepo.deleteById(id);
    }

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepo.findByUserId(userId);
    }

    @Override
    public List<Booking> getBookingsByHotelManager(Long managerId) {
        return bookingRepo.findBookingsByHotelManager(managerId);
    }

    @Override
    public List<Booking> getBookingsByHotelId(Long hotelId) {
        return bookingRepo.findByHotelId(hotelId);
    }

    @Override
    public boolean isBookingBelongToHotel(Long bookingId, Long hotelId) {
        return bookingRepo.isBookingBelongToHotel(bookingId, hotelId);
    }
}
