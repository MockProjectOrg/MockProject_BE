package org.example.bookingbe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.bookingbe.custom.datetime.DateTimeFormat;
import org.example.bookingbe.dto.BillDto;
import org.example.bookingbe.dto.DiscountDto;
import org.example.bookingbe.model.*;
import org.example.bookingbe.service.BillService.IBillService;
import org.example.bookingbe.service.BookingService.IBookingService;
import org.example.bookingbe.service.DiscountService.IDiscountService;
import org.example.bookingbe.service.ImageService.IImageService;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.example.bookingbe.service.UserService.IUserService;
import org.example.bookingbe.service.VNPayService.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.example.bookingbe.custom.datetime.DateTimeFormat.calculateDaysBetween;

@Controller
@RequestMapping("/api")
public class BookingController {
    @Autowired
    private IBillService billService;
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
    @Autowired
    private IImageService iImageService;
    @Autowired
    private IUserService userService;

    @GetMapping("/booking/{id}")
    private String booking(@PathVariable("id") Long id, Model model,
                           @RequestParam (required = false) String checkIn,
                           @RequestParam (required = false) String checkOut,
                           Principal principal, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<Room> room = roomService.getRoomById(id);
        Room roomOp = room.get();
        Optional<User> userOptional = userService.findByUsername(principal.getName());
        User user = userOptional.get();
        session.setAttribute("room", roomOp);
        session.setAttribute("user", user);
        session.setAttribute("checkIn", checkIn);
        session.setAttribute("checkOut", checkOut);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedCheckIn = checkIn != null ? LocalDateTime.parse(checkIn, inputFormatter).format(outputFormatter) : null;
        String formattedCheckOut = checkOut != null ? LocalDateTime.parse(checkOut, inputFormatter).format(outputFormatter) : null;
        List<Image> images = iImageService.findAllImagesByRoomId(id);
        List<DiscountDto> discountDto = discountService.getDiscounts(principal.getName(), id);
        long days = calculateDaysBetween(checkIn, checkOut);
        double priceTotal = room.get().getPrice() * days;
        model.addAttribute("images", images);
        model.addAttribute("discounts", discountDto);
        model.addAttribute("room", room);
        model.addAttribute("checkIn", formattedCheckIn);
        model.addAttribute("checkOut", formattedCheckOut);
        model.addAttribute("totalPrice", priceTotal);
        model.addAttribute("dateCheckIn", dateTimeFormatter.formatDate(checkIn));
        model.addAttribute("timeCheckIn", dateTimeFormatter.formatTime(checkIn));
        model.addAttribute("dateCheckOut", dateTimeFormatter.formatDate(checkOut));
        model.addAttribute("timeCheckOut", dateTimeFormatter.formatTime(checkOut));
        model.addAttribute("day", days);
        model.addAttribute("bill", new BillDto());
        return "client/payment";
    }


    @PostMapping("/pay")
    private String pay(@ModelAttribute("bill") BillDto bill, @RequestParam("price") Double price,
                       @RequestParam(required = false)String checkIn,
                       @RequestParam(required = false)String checkOut, HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Room room = (Room) session.getAttribute("room");
        Booking booking = new Booking(room,user,LocalDateTime.parse(checkIn, formatter), LocalDateTime.parse(checkOut,formatter), false);
        session.setAttribute("booking", bookingService.saveBooking(booking));
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(price, bill, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) throws MessagingException {
        HttpSession session = request.getSession();
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
        if(paymentStatus == 1){
            Booking booking = (Booking) session.getAttribute("booking");
            Double price = Double.valueOf(totalPrice) / 100;
            Bill bill = new Bill(LocalDateTime.now(), price, booking);
            billService.save(bill, (String) session.getAttribute("checkIn"), (String) session.getAttribute("checkOut"), billDto.getFirstName(),
                    billDto.getLastName(), billDto.getAddress(), billDto.getEmail(), billDto.getPhone(), price, paymentTime);
            booking.setStatus(true);
            bookingService.saveBooking(booking);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("paymentTime", paymentTime);
            model.addAttribute("transactionId", transactionId);
            return "client/orderSuccess";
        }
        session.removeAttribute("booking");
        session.removeAttribute("checkIn");
        session.removeAttribute("checkOut");
        session.removeAttribute("usser");
        session.removeAttribute("room");
        return "client/orderFail";
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
