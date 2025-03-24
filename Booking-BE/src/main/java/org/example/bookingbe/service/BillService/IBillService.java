package org.example.bookingbe.service.BillService;

import java.util.List;
import java.util.Map;

import jakarta.mail.MessagingException;
import org.example.bookingbe.model.Bill;

public interface IBillService {
    Bill save(Bill bill, String checkIn, String checkOut, String firstName, String lastName,String address, String email, String phone, Double price, String datePay) throws MessagingException;
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
