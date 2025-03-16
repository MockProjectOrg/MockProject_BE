package org.example.bookingbe.service.BookingService;

import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private IBookingRepo bookingRepo;

    @Override
    public int getTotalRevenue(int i) {
        return bookingRepo.getTotalRevenue();
    }
}
