package org.example.bookingbe.service.BookingService;

import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService implements IBookingService {
    private final IBookingRepo bookingRepo;

    @Autowired
    public BookingService(IBookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public Double getTotalRevenue() {
        return Optional.ofNullable(bookingRepo.countTotalRevenue()).orElse(0.0);
    }

    private Long countOrdersThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfWeek = today.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = today.with(DayOfWeek.SUNDAY).plusDays(1).atStartOfDay().minusSeconds(1);
        return Optional.ofNullable(bookingRepo.countOrdersThisWeek(startOfWeek, endOfWeek)).orElse(0L);
    }

    @Override
    public List<Map<String, Object>> getMonthlyRevenue() {
        return Optional.ofNullable(bookingRepo.getMonthlyRevenue())
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.mapping(row -> {
                    Map<String, Object> revenueMap = new HashMap<>();
                    revenueMap.put("month", ((Number) row[0]).intValue());
                    revenueMap.put("revenue", ((Number) row[1]).doubleValue());
                    return revenueMap;
                }, Collectors.toList()));
    }

    @Override
    public List<Map<String, Object>> getQuarterlyRevenue() {
        return Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> getYearlyRevenue() {
        return Collections.emptyList();
    }

    @Override
    public Double getPreviousMonthlyRevenue(int month, int year) {
        return 0.0;
    }

    @Override
    public Double getTotalRevenueThisYear() {
        return 0.0;
    }

    @Override
    public Double getTotalRevenueLastYear() {
        return 0.0;
    }

    @Override
    public Double getPreviousMonthlyRevenue() {
        return 0.0;
    }

    @Override
    public List<Map<String, Object>> getTopPackages() {
        return Collections.emptyList();
    }

    @Override
    public Map<String, Integer> getBookingCountsByStatus() {
        return Optional.ofNullable(bookingRepo.getBookingCountsByStatus())
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(
                        row -> row[0] != null ? row[0].toString() : "UNKNOWN",
                        row -> row[1] instanceof Number ? ((Number) row[1]).intValue() : 0
                ));
    }

    @Override
    public Long countCancelledBookings() {
        return Optional.ofNullable(bookingRepo.countCancelledBookings()).orElse(0L);
    }

    @Override
    public boolean isUserCheckedOut(Long userId, Long roomId) {
        return false;
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalOrders", Optional.ofNullable(bookingRepo.countTotalOrders()).orElse(0L));
        statistics.put("thisWeekOrders", countOrdersThisWeek());
        statistics.put("thisMonthlyOrders", Optional.ofNullable(bookingRepo.countOrdersThisMonth()).orElse(0L));
        statistics.put("totalRevenues", getTotalRevenue());
        statistics.put("thisMonthlyRevenues", Optional.ofNullable(bookingRepo.getRevenueThisMonth()).orElse(0.0));
        return statistics;
    }

    @Override
    public Integer getCountRoomAvailable() {
        return Math.toIntExact(Optional.ofNullable(bookingRepo.countAvailableRooms()).orElse(0L));
    }

    @Override
    public List<Map<String, Object>> getTopSelectedPackages() {
        return Collections.emptyList();
    }

    @Override
    public String getSalesChartUrl() {
        return "";
    }

    @Override
    public List<Object[]> getBookingDataByMonth() {
        return Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> getPopularRoomTypes() {
        List<Object[]> results = Optional.ofNullable(bookingRepo.getMostPopularRoomType())
                .orElse(Collections.emptyList());

        return results.stream().map(row -> {
            Map<String, Object> roomData = new HashMap<>();
            roomData.put("roomType", row[0]);  // Tên loại phòng
            roomData.put("bookingCount", ((Number) row[1]).intValue()); // Số lượt đặt
            return roomData;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getBookingCountsByMonth() {
        return Optional.ofNullable(bookingRepo.getBookingCountsByMonth())
                .orElse(Collections.emptyList())
                .stream()
                .map(row -> row != null && row.length > 1 && row[1] instanceof Number ? ((Number) row[1]).intValue() : 0)
                .collect(Collectors.toList());
    }
}
