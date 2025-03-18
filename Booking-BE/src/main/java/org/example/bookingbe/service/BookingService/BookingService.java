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
        return 0;
    }
/*
    public Map<String, Object> getStatistics() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalRevenue", bookingRepo.getTotalRevenue());
        data.put("totalOrders", bookingRepo.getTotalOrders());
        data.put("popularRoom", bookingRepo.getMostPopularRoomType());

        return data;
    }

    public List<Double> getMonthlyRevenue() {
        List<Double> monthlyRevenue = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Double revenue = bookingRepo.getRevenueByMonth(i);
            monthlyRevenue.add(revenue != null ? revenue : 0);
        }
        return monthlyRevenue;
    }

    public List<Double> getQuarterlyRevenue() {
        List<Double> monthlyRevenue = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Double revenue = bookingRepo.getRevenueByMonth(i);
            monthlyRevenue.add(revenue != null ? revenue : 0);
        }
        return monthlyRevenue;
    }

    public List<Double> getYearlyRevenue() {
        List<Double> monthlyRevenue = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Double revenue = bookingRepo.getRevenueByMonth(i);
            monthlyRevenue.add(revenue != null ? revenue : 0);
        }
        return monthlyRevenue;
    }

    @Override
    public int getTotalRevenue(int i) {
        return bookingRepo.getTotalRevenue();
    }
 */
}
