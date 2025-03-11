package org.example.bookingbe.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.bookingbe.model.Booking;
import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.User;
import org.example.bookingbe.service.BookingService.IBookingService;
import org.example.bookingbe.service.HotelService.IHotelService;
import org.example.bookingbe.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/managerBookings")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IHotelService hotelService;

    @GetMapping
    public String getManagerBookings(HttpSession session, Model model) {
        // Lấy hotelId và hotel từ session
        Long hotelId = (Long) session.getAttribute("hotelId");
        Hotel hotel = (Hotel) session.getAttribute("hotel");

        // Kiểm tra xem hotelId có tồn tại trong session không
        if (hotelId == null) {
            return "error"; // Hoặc redirect đến trang đăng nhập
        }

        // Nếu hotel không có trong session, lấy từ database
        if (hotel == null) {
            Optional<Hotel> optionalHotel = hotelService.getHotelById(hotelId);
            if (optionalHotel.isPresent()) {
                hotel = optionalHotel.get();
                session.setAttribute("hotel", hotel);
            } else {
                return "error"; // Hotel không tồn tại
            }
        }

        // Lấy danh sách booking của hotel đó
        List<Booking> bookings = bookingService.getBookingsByHotelId(hotelId);
        model.addAttribute("bookings", bookings);
        model.addAttribute("hotel", hotel);
        return "managerHotel/managerBookings";
    }

    // API để lọc bookings theo từ khóa tìm kiếm và ngày
    @GetMapping("/filter")
    @ResponseBody
    public List<Booking> filterBookings(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate filterDate,
            HttpSession session) {

        Long hotelId = (Long) session.getAttribute("hotelId");
        if (hotelId == null) {
            return List.of();
        }

        List<Booking> allBookings = bookingService.getBookingsByHotelId(hotelId);

        // Áp dụng bộ lọc nếu có
        if (searchTerm != null && !searchTerm.isEmpty()) {
            String searchLower = searchTerm.toLowerCase();
            allBookings = allBookings.stream()
                    .filter(booking ->
                            (booking.getRoom() != null && booking.getRoom().getDescription() != null
                                    && booking.getRoom().getDescription().toLowerCase().contains(searchLower)) ||
                                    (booking.getUser() != null && booking.getUser().getUserName() != null
                                            && booking.getUser().getUserName().toLowerCase().contains(searchLower)))
                    .collect(Collectors.toList());
        }

        if (filterDate != null) {
            LocalDateTime filterDateTime = filterDate.atStartOfDay();
            LocalDateTime nextDay = filterDate.plusDays(1).atStartOfDay();

            allBookings = allBookings.stream()
                    .filter(booking ->
                            (booking.getCheckIn().isAfter(filterDateTime) || booking.getCheckIn().isEqual(filterDateTime)) &&
                                    booking.getCheckIn().isBefore(nextDay))
                    .collect(Collectors.toList());
        }

        return allBookings;
    }

    @GetMapping("/{id}")
    public String getBookingDetails(@PathVariable Long id, HttpSession session, Model model) {
        Long hotelId = (Long) session.getAttribute("hotelId");
        Hotel hotel = (Hotel) session.getAttribute("hotel");

        if (hotelId == null) {
            return "error";
        }

        // Kiểm tra booking có thuộc về hotel của manager không
        if (!bookingService.isBookingBelongToHotel(id, hotelId)) {
            return "error"; // Booking không thuộc về hotel này
        }

        Optional<Booking> booking = bookingService.getBookingById(id);
        if (booking.isPresent()) {
            model.addAttribute("booking", booking.get());
            model.addAttribute("hotel", hotel);
            return "managerHotel/bookingDetails";
        } else {
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id, HttpSession session) {
        Long hotelId = (Long) session.getAttribute("hotelId");
        if (hotelId == null) {
            return "error";
        }

        // Kiểm tra booking có thuộc về hotel của manager không
        if (!bookingService.isBookingBelongToHotel(id, hotelId)) {
            return "error"; // Booking không thuộc về hotel này
        }

        bookingService.deleteBooking(id);
        return "redirect:/managerBookings";
    }

    @GetMapping("/view/{id}")
    @ResponseBody
    public ResponseEntity<Booking> viewBookingDetails(@PathVariable Long id, HttpSession session) {
        Long hotelId = (Long) session.getAttribute("hotelId");
        if (hotelId == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!bookingService.isBookingBelongToHotel(id, hotelId)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Booking> booking = bookingService.getBookingById(id);
        if (booking.isPresent()) {
            return ResponseEntity.ok(booking.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/print")
    public String printBookingList(HttpSession session, Model model) {
        Long hotelId = (Long) session.getAttribute("hotelId");
        if (hotelId == null) {
            return "error";
        }

        List<Booking> bookings = bookingService.getBookingsByHotelId(hotelId);
        model.addAttribute("bookings", bookings);
        model.addAttribute("printDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        // Trả về view dành cho in ấn
        return "managerHotel/printBookings";
    }
}