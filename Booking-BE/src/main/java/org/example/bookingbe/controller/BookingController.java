package org.example.bookingbe.controller;

import org.example.bookingbe.service.BookingService.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Lấy tổng số đơn đặt phòng và doanh thu
    @GetMapping("/total-bookings")
    public String getTotalBookings(Model model) {
        Map<String, Object> statistics = bookingService.getStatistics();

        Long totalBookings = Optional.ofNullable(statistics.get("totalOrders"))
                .map(val -> (Long) val).orElse(0L);

        Double totalRevenue = Optional.ofNullable(statistics.get("totalRevenues"))
                .map(val -> (Double) val).orElse(0.0);

        model.addAttribute("totalBookings", totalBookings);
        model.addAttribute("totalRevenue", totalRevenue);

        return "/adminHotel/adminStatistics";
    }

    // Tính phần trăm thay đổi doanh thu giữa tháng hiện tại và tháng trước
    public Double getPercentageChangeInRevenue() {
        // Lấy danh sách doanh thu theo tháng
        List<Map<String, Object>> monthlyRevenueList = bookingService.getMonthlyRevenue();
        // Lấy doanh thu tháng hiện tại
        int currentMonth = LocalDate.now().getMonthValue();
        Double currentRevenue = monthlyRevenueList.stream()
                .filter(map -> (int) map.get("month") == currentMonth)
                .map(map -> (Double) map.get("revenue"))
                .findFirst()
                .orElse(0.0);

        // Lấy doanh thu tháng trước
        int previousMonth = currentMonth - 1;
        int currentYear = LocalDate.now().getYear();
        if (previousMonth == 0) { // Nếu tháng trước là tháng 12 của năm trước
            previousMonth = 12;
            currentYear -= 1;
        }
        Double previousRevenue = bookingService.getPreviousMonthlyRevenue(previousMonth, currentYear);

        // Tránh lỗi chia cho 0
        if (previousRevenue == 0.0) {
            return 0.0;
        }

        return ((currentRevenue - previousRevenue) / previousRevenue) * 100;
    }

    @GetMapping("/admin/Revenuechart")
    public String getRevenueChart(Model model) {
        List<Map<String, Object>> revenueData = bookingService.getMonthlyRevenue();

        // Tạo danh sách nhãn (tháng) và dữ liệu doanh thu
        List<String> labels = revenueData.stream()
                .map(row -> "Tháng " + row.get("month"))
                .toList();

        List<Double> revenues = revenueData.stream()
                .map(row -> (Double) row.get("revenue"))
                .toList();

        // Đưa dữ liệu vào model để Thymeleaf sử dụng
        model.addAttribute("labels", labels);
        model.addAttribute("revenues", revenues);

        return "adminHotel/revenueChart"; // Trả về trang Thymeleaf
    }

    @GetMapping("/admin/popular-rooms")
    public String getPopularRooms(Model model) {
        List<Map<String, Object>> popularRooms = bookingService.getPopularRoomTypes();

        // Lấy danh sách tên phòng và số lượt đặt
        List<String> roomTypes = popularRooms.stream()
                .map(room -> room.get("roomType").toString())
                .toList();

        List<Integer> bookingCounts = popularRooms.stream()
                .map(room -> ((Number) room.get("bookingCount")).intValue())
                .toList();

        model.addAttribute("roomTypes", roomTypes);
        model.addAttribute("bookingCounts", bookingCounts);

        return "adminHotel/adminStatistics"; // Trả về trang Dashboard
    }

    // Lấy thông tin dashboard
    @GetMapping("/admin/Dashboard")
    public String getDashboard(Model model) {
        Map<String, Object> statistics = bookingService.getStatistics();

        Double currentRevenue = bookingService.getMonthlyRevenue()
                .stream()
                .filter(map -> (int) map.get("month") == LocalDate.now().getMonthValue())
                .map(map -> (Double) map.get("revenue"))
                .findFirst()
                .orElse(0.0);

        Double percentageChange = getPercentageChangeInRevenue();
        Integer countRoomAvailable = bookingService.getCountRoomAvailable();
        List<Map<String, Object>> topPackages = bookingService.getTopSelectedPackages();
        String salesChartUrl = bookingService.getSalesChartUrl();

        // Lấy dữ liệu đặt phòng theo tháng
        List<Object[]> rawData = bookingService.getBookingDataByMonth();
        List<String> labels = new ArrayList<>();
        List<Integer> bookingData = new ArrayList<>();
        for (Object[] row : rawData) {
            labels.add(row[0].toString());
            bookingData.add(Integer.parseInt(row[1].toString()));
        }

        // Lấy dữ liệu loại phòng phổ biến
        List<Map<String, Object>> popularRooms = bookingService.getPopularRoomTypes();
        List<String> roomTypes = popularRooms.stream()
                .map(room -> room.get("roomType").toString())
                .toList();
        List<Integer> bookingCounts = popularRooms.stream()
                .map(room -> ((Number) room.get("bookingCount")).intValue())
                .toList();

        // Đưa vào Model
        model.addAttribute("labels", labels);
        model.addAttribute("bookingData", bookingData);
        model.addAttribute("totalOrders", statistics.get("totalOrders"));
        model.addAttribute("totalRevenues", statistics.get("totalRevenues"));
        model.addAttribute("monthlyRevenue", currentRevenue);
        model.addAttribute("percentageChange", percentageChange);
        model.addAttribute("countRoomAvailable", countRoomAvailable);
        model.addAttribute("topPackages", topPackages);
        model.addAttribute("salesChartUrl", salesChartUrl);
        model.addAttribute("roomTypes", roomTypes);
        model.addAttribute("bookingCounts", bookingCounts);

        return "adminHotel/adminStatistics";
    }
}
