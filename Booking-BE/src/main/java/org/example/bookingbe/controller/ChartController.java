package org.example.bookingbe.controller;

import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChartController {

    private final IBookingRepo bookingRepo;

    public ChartController(IBookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @GetMapping("/BookingChart")
    public String getBookingChartData(Model model) {
        List<Object[]> rawData = bookingRepo.countBookingsByMonth();
        List<Object[]> revenueData = bookingRepo.revenueByRoomType(); // Doanh thu theo loại phòng

        // Biểu đồ cột: Số lượng đặt phòng theo tháng
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int currentMonth = LocalDate.now().getMonthValue();

        List<String> labels = new ArrayList<>();
        List<Integer> bookingData = new ArrayList<>();
        int[] bookingsByMonth = new int[12];

        for (Object[] row : rawData) {
            if (row[0] != null && row[1] != null) {
                int month = ((Number) row[0]).intValue();
                int count = ((Number) row[1]).intValue();
                bookingsByMonth[month - 1] = count;
            }
        }

        for (int i = 0; i < currentMonth; i++) {
            labels.add(monthNames[i]);
            bookingData.add(bookingsByMonth[i]);
        }

        // Biểu đồ tròn: Doanh thu theo loại phòng
        List<String> roomLabels = new ArrayList<>();
        List<Double> roomRevenue = new ArrayList<>();

        for (Object[] row : revenueData) {
            if (row[0] != null && row[1] != null) {
                roomLabels.add(row[0].toString()); // Loại phòng
                roomRevenue.add(((Number) row[1]).doubleValue()); // Doanh thu
            }
        }

        // Đưa dữ liệu vào model để Thymeleaf sử dụng
        model.addAttribute("labels", labels);
        model.addAttribute("bookingData", bookingData);
        model.addAttribute("roomLabels", roomLabels);
        model.addAttribute("roomRevenue", roomRevenue);

        return "adminHotel/revenue-chart";
    }

}
