package org.example.bookingbe.controller;

import org.example.bookingbe.service.BookingService.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("booking-chart")
public class BookingChartController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String showBookingChart(Model model) {
        List<Integer> bookingCounts = bookingService.getBookingCountsByMonth();
        model.addAttribute("bookingCounts", bookingCounts);
        return "bookingChart"; // Trả về file bookingChart.html
    }

    @GetMapping("/data")
    public ResponseEntity<List<Integer>> getBookingChartData() {
        return ResponseEntity.ok(bookingService.getBookingCountsByMonth());
    }
}
