package org.example.bookingbe.controller;

import org.example.bookingbe.service.BookingService.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/booking")
public class UserBookingController {

    @Autowired
    private IBookingService bookingService;

    @PostMapping("/book")
    public String bookRoom(@RequestParam Long userId,
                           @RequestParam Long roomId,
                           @RequestParam String checkIn,
                           @RequestParam String checkOut,
                           @RequestParam String description,
                           RedirectAttributes redirectAttributes) {
        try {
            LocalDateTime checkInDate = LocalDateTime.parse(checkIn);
            LocalDateTime checkOutDate = LocalDateTime.parse(checkOut);

            bookingService.saveBooking(userId, roomId, checkInDate, checkOutDate, description);
            redirectAttributes.addFlashAttribute("success", "Đặt phòng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/booking";
    }

    @PostMapping("/cancel")
    public String cancelBooking(@RequestParam Long bookingId,
                                @RequestParam Long userId,
                                RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancelBooking(bookingId, userId);
            redirectAttributes.addFlashAttribute("success", "Hủy đặt phòng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/booking";
    }
}
