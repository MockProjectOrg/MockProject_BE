package org.example.bookingbe.service.BookingService;

import java.util.List;
import java.util.Map;

public interface IBookingService {

    Double getTotalBookings(); // Đổi tên cho rõ nghĩa hơn (tổng số booking)

    Double getTotalRevenue(); // Tổng doanh thu

    Double getRevenueCurrentWeek(); // Doanh thu tuần này

    List<Object[]> getMonthlyRevenue(); // Doanh thu từng tháng

    Double getTotalOrder();

    List<Object[]> getQuarterlyRevenue(); // Doanh thu theo quý

    List<Object[]> getYearlyRevenue(); // Doanh thu theo năm

    Map<String, Object> getStatistics(); // Tổng hợp số liệu

    Object getTopPackages();

    List<Integer> getBookingCountsByMonth();
}
