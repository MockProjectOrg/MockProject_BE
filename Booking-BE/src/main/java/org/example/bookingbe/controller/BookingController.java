package org.example.bookingbe.controller;

import org.example.bookingbe.service.BookingService.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/total")
    public int getToalOrder() {
        return bookingService.getTotalRevenue(0);
    }
}
