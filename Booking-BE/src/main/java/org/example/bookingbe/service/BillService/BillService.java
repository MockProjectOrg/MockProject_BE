package org.example.bookingbe.service.BillService;

import org.example.bookingbe.repository.BillRepo.IBillRepo;
import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillService implements IBillService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IBookingRepo bookingRepo;
    @Autowired
    private IBillRepo billRepo;

    // 🔹 Doanh thu theo từng tháng
    @Override
    public List<Map<String, Object>> getMonthlyRevenue() {
        String sql = "SELECT MONTH(date_payment) AS month, COALESCE(SUM(total_price), 0) AS revenue FROM bill GROUP BY MONTH(date_payment)";
        return jdbcTemplate.queryForList(sql);
    }

    // 🔹 Tổng doanh thu năm trước
    @Override
    public Double getTotalRevenueLastYear() {
        int lastYear = LocalDate.now().getYear() - 1;
        String sql = "SELECT COALESCE(SUM(total_price), 0) FROM bill WHERE YEAR(date_payment) = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, lastYear);
    }

    // 🔹 Doanh thu của tháng trước
    @Override
    public Double getPreviousMonthlyRevenue(int month, int year) {
        if (month == 1) {
            month = 12;
            year -= 1;
        } else {
            month -= 1;
        }
        String sql = "SELECT COALESCE(SUM(total_price), 0) FROM bill WHERE YEAR(date_payment) = ? AND MONTH(date_payment) = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, year, month);
    }

    // 🔹 Doanh thu theo quý
    @Override
    public List<Map<String, Object>> getQuarterlyRevenue() {
        String sql = "SELECT QUARTER(date_payment) AS quarter, COALESCE(SUM(total_price), 0) AS revenue FROM bill GROUP BY QUARTER(date_payment)";
        return jdbcTemplate.queryForList(sql);
    }

    // 🔹 Doanh thu theo năm
    @Override
    public List<Map<String, Object>> getYearlyRevenue() {
        String sql = "SELECT YEAR(date_payment) AS year, COALESCE(SUM(total_price), 0) AS revenue FROM bill GROUP BY YEAR(date_payment)";
        return jdbcTemplate.queryForList(sql);
    }

    // 🔹 Tổng doanh thu năm hiện tại
    @Override
    public Double getTotalRevenueThisYear() {
        int currentYear = LocalDate.now().getYear();
        String sql = "SELECT COALESCE(SUM(total_price), 0) FROM bill WHERE YEAR(date_payment) = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, currentYear);
    }

    // 🔹 Doanh thu theo tháng và năm cụ thể
    @Override
    public Double getMonthlyRevenue(int month, int year) {
        String sql = "SELECT COALESCE(SUM(total_price), 0) FROM bill WHERE YEAR(date_payment) = ? AND MONTH(date_payment) = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, year, month);
    }

    @Override
    public List<Integer> getBookingCountsByMonth() {
        List<Object[]> results = billRepo.countBookingsByMonth();
        if (results == null) {
            return Collections.emptyList();
        }

        return results.stream()
                .map(row -> (row != null && row.length > 1 && row[1] instanceof Number) ? ((Number) row[1]).intValue() : 0)
                .collect(Collectors.toList());
    }

    // 🔹 Lấy thống kê tổng quan
    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        statistics.put("totalOrders", Optional.ofNullable(bookingRepo.countTotalOrders()).orElse(0L));
        statistics.put("thisMonthlyOrders", Optional.ofNullable(bookingRepo.countOrdersThisMonth()).orElse(0L));
        statistics.put("totalRevenues", getTotalRevenueThisYear());

        return statistics;
    }
}
