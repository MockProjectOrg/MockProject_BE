package org.example.bookingbe.controller;

import org.example.bookingbe.dto.BookingDto;
import org.example.bookingbe.model.Booking;
import org.example.bookingbe.service.BookingService.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class BookingController {
    @Autowired
    private IBookingService bookingService;

    @GetMapping("/booking/{id}")
    private String booking(@PathVariable("id") Long id, Model model) {
        BookingDto booking = bookingService.getBooking(id);

        if (booking == null) {
            throw new RuntimeException("Booking not found!");
        }
        model.addAttribute("booking", booking);
        return "client/payment";
    }
}
