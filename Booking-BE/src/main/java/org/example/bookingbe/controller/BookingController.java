package org.example.bookingbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.example.bookingbe.custom.datetime.DateTimeFormat;
import org.example.bookingbe.dto.BillDto;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.service.BillService.IBillService;
import org.example.bookingbe.service.BookingService.BookingService;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.example.bookingbe.service.VNPayService.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.example.bookingbe.custom.datetime.DateTimeFormat.calculateDaysBetween;

@Controller
@RequestMapping("/api")
public class BookingController {
    private final BookingService bookingService;
    @Autowired
    private DateTimeFormat dateTimeFormatter;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private IBillService billService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/booking/{id}")
    private String booking(@PathVariable("id") Long id, Model model,
                           @RequestParam(required = false) String checkIn,
                           @RequestParam(required = false) String checkOut) {
        Optional<Room> room = roomService.getRoomById(id);
        long days = calculateDaysBetween(checkIn, checkOut);
        double priceTotal = room.get().getPrice() * days;
        model.addAttribute("room", room);
        model.addAttribute("totalPrice", priceTotal);
        model.addAttribute("timeCheckIn", dateTimeFormatter.formatTime(checkIn));
        model.addAttribute("dateCheckOut", dateTimeFormatter.formatDate(checkOut));
        model.addAttribute("timeCheckOut", dateTimeFormatter.formatTime(checkOut));
        return "client/payment";
    }

    @PostMapping("/pay")
    private String pay(@ModelAttribute("bill") BillDto bill, @RequestParam("price") Double price, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(price, bill, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        BillDto billDto = null;

        if (orderInfo != null) {
            try {
                // 🔥 Giải mã chuỗi JSON từ URL
                String orderInfoJson = URLDecoder.decode(orderInfo, StandardCharsets.UTF_8.toString());
                ObjectMapper objectMapper = new ObjectMapper();
                billDto = objectMapper.readValue(orderInfoJson, BillDto.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(billDto.getPrice());
//        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "client/orderSuccess" : "client/orderFail";
    }

    // Lấy tổng số đơn đặt phòng và doanh thu
    @GetMapping("/total-bookings")
    public String getTotalBookings(Model model) {
        Map<String, Object> statistics = billService.getStatistics();

        Long totalBookings = Optional.ofNullable(statistics.get("totalOrders"))
                .map(val -> (Long) val).orElse(0L);

        Double totalRevenue = Optional.ofNullable(statistics.get("totalRevenues"))
                .map(val -> (Double) val).orElse(0.0);

        model.addAttribute("totalBookings", totalBookings);
        model.addAttribute("totalRevenue", totalRevenue);

        return "/adminHotel/adminStatistics";
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
}
