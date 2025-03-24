package org.example.bookingbe.service.ChartService;

import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartService {
    private final IBookingRepo bookingRepo;

    public ChartService(IBookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public Map<String, Object> getBookingData() {
        List<Object[]> results = bookingRepo.countBookingsByMonth();

        System.out.println("Raw Query Results: " + results); // ✅ Debug

        List<String> labels = results.stream().map(row -> row[0].toString()).toList();
        List<Integer> bookingData = results.stream().map(row -> Integer.parseInt(row[1].toString())).toList();

        System.out.println("Labels: " + labels);  // ✅ Debug
        System.out.println("Booking Data: " + bookingData);  // ✅ Debug

        return Map.of("labels", labels, "bookingData", bookingData);
    }
}
