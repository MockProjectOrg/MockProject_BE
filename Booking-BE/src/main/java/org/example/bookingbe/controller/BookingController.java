package org.example.bookingbe.controller;

import org.example.bookingbe.service.BookingService.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/admin/Dashboard")
    public String getDashboard(Model model) {
        // Gọi một lần duy nhất
        Map<String, Object> statistics = bookingService.getStatistics();

        // Thêm dữ liệu vào model
        model.addAttribute("totalRevenue", statistics.get("totalRevenue"));
        model.addAttribute("totalOrders", statistics.get("totalOrders"));
        model.addAttribute("popularRoom", statistics.get("popularRoom"));
        model.addAttribute("countRoomAvailable", statistics.get("countRoomAvailable"));
        model.addAttribute("bookedRoomsWeekly", statistics.get("bookedRoomsWeekly"));
        model.addAttribute("bookedRoomsMonthly", statistics.get("bookedRoomsMonthly"));
        model.addAttribute("topPackages", bookingService.getTopPackages());

        return "adminHotel/adminStatistics";
    }
}
