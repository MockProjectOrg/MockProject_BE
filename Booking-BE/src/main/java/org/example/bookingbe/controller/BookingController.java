package org.example.bookingbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.example.bookingbe.custom.datetime.DateTimeFormat;
import org.example.bookingbe.dto.BillDto;
import org.example.bookingbe.dto.DiscountDto;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.service.BookingService.IBookingService;
import org.example.bookingbe.service.DiscountService.IDiscountService;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.example.bookingbe.service.VNPayService.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.example.bookingbe.custom.datetime.DateTimeFormat.calculateDaysBetween;

@Controller
@RequestMapping("/api")
public class BookingController {
    @Autowired
    private DateTimeFormat dateTimeFormatter;
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IDiscountService discountService;
    @Autowired
    private VNPayService vnPayService;
    @GetMapping("/booking/{id}")
    private String booking(@PathVariable("id") Long id, Model model,
                           @RequestParam (required = false) String checkIn,
                           @RequestParam (required = false) String checkOut,
                           Principal principal) {
        System.out.println("user_name" + principal.getName());
        List<DiscountDto> discountDto = discountService.getDiscounts(principal.getName(), id);
        Optional<Room> room = roomService.getRoomById(id);
        long days = calculateDaysBetween(checkIn, checkOut);
        double priceTotal = room.get().getPrice() * days;
        model.addAttribute("room", room);
        model.addAttribute("totalPrice", priceTotal);
        model.addAttribute("dateCheckIn", dateTimeFormatter.formatDate(checkIn));
        model.addAttribute("timeCheckIn", dateTimeFormatter.formatTime(checkIn));
        model.addAttribute("dateCheckOut", dateTimeFormatter.formatDate(checkOut));
        model.addAttribute("timeCheckOut", dateTimeFormatter.formatTime(checkOut));
        model.addAttribute("day", days);
        return "client/payment";
    }


    @PostMapping("/pay")
    private String pay(@ModelAttribute("bill") BillDto bill, @RequestParam("price") Double price, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(price, bill, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

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
}
