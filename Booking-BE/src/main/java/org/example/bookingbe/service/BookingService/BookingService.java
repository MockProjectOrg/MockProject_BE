package org.example.bookingbe.service.BookingService;

import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepo bookingRepo;

    @Override
    public Double getTotalRevenue() {
        return bookingRepo.getTotalRevenue("COMPLETED");
    }

    @Override
    public Double getTotalOrder() {
        return (double) bookingRepo.countTotalBookings();
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalRevenue", bookingRepo.getTotalRevenue("COMPLETED"));
        data.put("totalOrders", bookingRepo.countTotalBookings());

        String popularRoom = Optional.ofNullable(bookingRepo.getMostPopularRoomType()).orElse("N/A");
        data.put("popularRoom", popularRoom);

        return data;
    }

    public List<Double> getMonthlyRevenue() {
        return IntStream.rangeClosed(1, 12)
                .mapToObj(i -> Optional.ofNullable(bookingRepo.getRevenueByMonth(i)).orElse(0.0))
                .collect(Collectors.toList());
    }

    public List<Double> getQuarterlyRevenue() {
        return IntStream.rangeClosed(1, 4)
                .mapToObj(i -> Optional.ofNullable(bookingRepo.getRevenueByQuarter(i)).orElse(0.0))
                .collect(Collectors.toList());
    }

    public List<Double> getYearlyRevenue() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return IntStream.range(0, 5)
                .mapToObj(i -> Optional.ofNullable(bookingRepo.getRevenueByYear(currentYear - i)).orElse(0.0))
                .collect(Collectors.toList());
    }
}
