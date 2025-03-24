package org.example.bookingbe.service.BillService;

import java.util.List;
import java.util.Map;

public interface IBillService {
    // doanh thu từng tháng
    List<Map<String, Object>> getMonthlyRevenue();

    List<Map<String, Object>> getQuarterlyRevenue();

    List<Map<String, Object>> getYearlyRevenue();

    Double getTotalRevenueThisYear();

    // doanh thu năm trước
    Double getTotalRevenueLastYear();

    // Doanh thu tháng trước
    Double getPreviousMonthlyRevenue(int month, int year);


    Double getMonthlyRevenue(int month, int year);

    // lấy số lượng đặt phòng theo tháng
    List<Integer> getBookingCountsByMonth();

    Map<String, Object> getStatistics();
}
