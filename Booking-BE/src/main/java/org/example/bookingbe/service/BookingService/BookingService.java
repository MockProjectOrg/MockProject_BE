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

    //Đếm số lươợng đơn booking
    @Override
    public Double getTotalBookings() {
        return bookingRepo.countTotalBookings();
    }

    // đếm tổng doanh thu
    @Override
    public Double getTotalRevenue() {
        return bookingRepo.getTotalRevenue();
    }

    // tính doanh thu trong tuần hiện tại
    @Override
    public Double getRevenueCurrentWeek() {
        return bookingRepo.getRevenueCurrentWeek();
    }

    // tính doanh thu theo tháng
    @Override
    public List<Object[]> getMonthlyRevenue() {
        return bookingRepo.getMonthlyRevenue();
    }

    // tính doanh thu theo quý
    @Override
    public List<Object[]> getQuarterlyRevenue() {
        return bookingRepo.getQuarterlyRevenue();
    }

    //tính doanh thu theo năm
    @Override
    public List<Object[]> getYearlyRevenue() {
        return bookingRepo.getYearlyRevenue();
    }

    @Override
    public List<Map<String, Object>> getTopPackages() {
        List<Object[]> results = bookingRepo.getMostBookedPackages();
        List<Map<String, Object>> topPackages = new ArrayList<>();

        if (results.isEmpty()) {
            System.out.println("Không có dữ liệu đặt phòng!");
            return topPackages;
        }

        // Tính tổng số lượt đặt phòng để tính phần trăm
        int totalBookings = results.stream().mapToInt(r -> ((Number) r[1]).intValue()).sum();
        System.out.println("🔹 Tổng số lượt đặt phòng: " + totalBookings);

        String[] colors = {"#FF5733", "#33FF57", "#5733FF", "#FFC300", "#C70039"};
        int colorIndex = 0;

        for (Object[] row : results) {
            String packageName = (String) row[0];
            int count = ((Number) row[1]).intValue();
            double percentage = totalBookings > 0 ? ((double) count / totalBookings) * 100 : 0;

            Map<String, Object> packageInfo = new HashMap<>();
            packageInfo.put("packageName", packageName);
            packageInfo.put("percentage", percentage);
            packageInfo.put("color", colors[colorIndex % colors.length]);

            System.out.println("Gói: " + packageName + ", Số lượng: " + count + ", Phần trăm: " + percentage);
            topPackages.add(packageInfo);
            colorIndex++;
        }

        return topPackages;
    }

    private String getColorForPackage(String packageName) {
        Map<String, String> colorMap = Map.of(
                "Standard", "#f87171",
                "Premium", "#60a5fa",
                "Deluxe", "#34d399"
        );
        return colorMap.getOrDefault(packageName, "#a3a3a3");
    }

    @Override
    public List<Integer> getBookingCountsByMonth() {
        List<Object[]> results = bookingRepo.getBookingCountsByMonth();
        List<Integer> bookingCounts = new ArrayList<>();

        for (Object[] row : results) {
            bookingCounts.add(((Number) row[1]).intValue());
        }
        return bookingCounts;
    }

    @Override
    public Map<String, Object> getStatistics() {
        List<Object[]> results = bookingRepo.getStatistics();
        Map<String, Object> statistics = new HashMap<>();

        if (!results.isEmpty()) {
            Object[] row = results.get(0);
            statistics.put("totalBookings", row[0] != null ? ((Number) row[0]).intValue() : 0);
            statistics.put("totalRevenue", row[1] != null ? ((Number) row[1]).doubleValue() : 0.0);
        } else {
            statistics.put("totalBookings", 0);
            statistics.put("totalRevenue", 0.0);
        }

        return statistics;
    }
}