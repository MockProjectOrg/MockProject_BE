package org.example.bookingbe.controller;

import org.example.bookingbe.repository.BillRepo.IBillRepo;
import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.example.bookingbe.service.BillService.IBillService;
import org.example.bookingbe.service.BookingService.IBookingService;
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
@RequestMapping("/AdminManager")
public class DashboardController {
    private final IBookingRepo bookingRepo;
    private final IBillRepo billRepo;
    private final IBookingService bookingService;
    private final IBillService billService;

    public DashboardController(IBookingRepo bookingRepo, IBillRepo billRepo, IBookingService bookingService, IBillService billService) {
        this.bookingRepo = bookingRepo;
        this.billRepo = billRepo;
        this.bookingService = bookingService;
        this.billService = billService;
    }

    private Double getPercentageChangeInRevenue() {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        // Nếu là tháng 1, thì lấy tháng 12 của năm trước
        int previousMonth = (currentMonth == 1) ? 12 : currentMonth - 1;
        int previousYear = (currentMonth == 1) ? currentYear - 1 : currentYear;

        Double currentMonthRevenue = Optional.ofNullable(billService.getPreviousMonthlyRevenue(currentYear, currentMonth)).orElse(0.0);
        Double previousMonthRevenue = Optional.ofNullable(billService.getPreviousMonthlyRevenue(previousYear, previousMonth)).orElse(0.0);

        if (previousMonthRevenue == 0) {
            return (currentMonthRevenue > 0) ? 100.0 : 0.0; // Nếu tháng trước không có doanh thu
        }

        return ((currentMonthRevenue - previousMonthRevenue) / previousMonthRevenue) * 100;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Thống kê đặt phòng
        Map<String, Object> statistics = billService.getStatistics();
        // Doanh thu tháng hiện tại
        Double currentRevenue = billService.getMonthlyRevenue()
                .stream()
                .filter(map -> map.get("month") instanceof Number && ((Number) map.get("month")).intValue() == LocalDate.now().getMonthValue())
                .map(map -> map.get("revenue") instanceof Number ? ((Number) map.get("revenue")).doubleValue() : 0.0)
                .findFirst()
                .orElse(0.0);
        // Tính toán phần trăm thay đổi doanh thu
        Double percentageChange = getPercentageChangeInRevenue();

        // Số lượng phòng trống
        Integer countRoomAvailable = bookingService.getCountRoomAvailable();
        // URL biểu đồ doanh số
        String salesChartUrl = bookingService.getSalesChartUrl();

        // Dữ liệu đặt phòng theo tháng
        List<Object[]> rawData = bookingService.getBookingDataByMonth();
        List<String> labels = new ArrayList<>();
        List<Integer> bookingData = new ArrayList<>();
        for (Object[] row : rawData) {
            labels.add(row[0].toString());
            bookingData.add(Integer.parseInt(row[1].toString()));
        }

        // Dữ liệu loại phòng phổ biến
        List<Map<String, Object>> popularRooms = bookingService.getPopularRoomTypes();
        List<String> roomTypes = new ArrayList<>();
        List<Integer> bookingCounts = new ArrayList<>();
        for (Map<String, Object> room : popularRooms) {
            roomTypes.add(room.get("roomType").toString());
            bookingCounts.add(((Number) room.get("bookingCount")).intValue());
        }

        // Tổng doanh thu năm nay (sửa lại dùng `billService`)
        Double totalRevenue = billService.getTotalRevenueThisYear();

        // Tổng số đơn đặt phòng
        Long totalOrders = bookingRepo.countTotalOrders();

        // Số đơn bị hủy
        Long cancelledOrders = bookingRepo.countCancelledBookings();

        // Số lượng phòng trống
        Long availableRooms = bookingRepo.countAvailableRooms();

        // Đưa dữ liệu vào model
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("labels", labels);
        model.addAttribute("bookingData", bookingData);
        model.addAttribute("totalRevenues", statistics.get("totalRevenues"));
        model.addAttribute("monthlyRevenue", currentRevenue);
        model.addAttribute("percentageChange", percentageChange);
        model.addAttribute("countRoomAvailable", countRoomAvailable);
        model.addAttribute("salesChartUrl", salesChartUrl);
        model.addAttribute("roomTypes", roomTypes);
        model.addAttribute("bookingCounts", bookingCounts);
        return "adminHotel/adminStatistics";
    }
}
