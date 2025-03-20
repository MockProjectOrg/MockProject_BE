package org.example.bookingbe.service.BookingService;

import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingService implements IBookingService {

    private final IBookingRepo bookingRepo;

    public BookingService(IBookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public Double getTotalBookings() {
        return bookingRepo.countTotalBookings();
    }

    @Override
    public Double getTotalRevenue() {
        return bookingRepo.getTotalRevenue();
    }

    @Override
    public Double getRevenueCurrentWeek() {
        return bookingRepo.getRevenueCurrentWeek();
    }

    @Override
    public List<Object[]> getMonthlyRevenue() {
        return bookingRepo.getMonthlyRevenue();
    }

    @Override
    public Double getTotalOrder() {
        return (double) bookingRepo.countTotalBookings();
    }

    @Override
    public List<Object[]> getQuarterlyRevenue() {
        return bookingRepo.getQuarterlyRevenue();
    }

    @Override
    public List<Object[]> getYearlyRevenue() {
        return bookingRepo.getYearlyRevenue();
    }

    @Override
    public Map<String, Object> getStatistics() {
        return bookingRepo.getStatistics();
    }

    @Override
    public List<Map<String, Object>> getTopPackages() {
        List<Object[]> results = bookingRepo.getTopPackages();
        List<Map<String, Object>> topPackages = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> packageData = new HashMap<>();
            packageData.put("packageName", row[0]);
            packageData.put("totalBookings", row[1]);

            topPackages.add(packageData);
        }
        return topPackages;
    }
}