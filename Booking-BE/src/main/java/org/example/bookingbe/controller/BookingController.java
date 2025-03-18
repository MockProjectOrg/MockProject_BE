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
    @Autowired
    private BookingService bookingService;

    @GetMapping("/admin/Dashboard")
    public String getDashboard(Model model) {
        Map<String, Object> statistics = bookingService.getStatistics();
        model.addAttribute("totalRevenue", statistics.get("totalRevenue"));
        model.addAttribute("totalOrders", statistics.get("totalOrders"));
        model.addAttribute("popularRoom", statistics.get("popularRoom"));

        return "adminStatistics";
    }
}
