package org.example.bookingbe.service.BookingService;

import java.util.List;
import java.util.Map;

public interface IBookingService {

    Double getTotalBookings(); //(tổng số booking)

    Double getTotalRevenue(); // Tổng doanh thu

    Double getRevenueCurrentWeek(); // Doanh thu tuần này

    List<Object[]> getMonthlyRevenue(); // Doanh thu từng tháng

    List<Object[]> getQuarterlyRevenue(); // Doanh thu theo quý

    List<Object[]> getYearlyRevenue(); // Doanh thu theo năm

    List<Map<String, Object>> getTopPackages();

    List<Integer> getBookingCountsByMonth();

    Map<String, Object> getStatistics();
}
