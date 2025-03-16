// CheckoutNotificationScheduler.java
package org.example.bookingbe.controller;

import org.example.bookingbe.model.Booking;
import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.example.bookingbe.service.NotifyService.INotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@EnableScheduling
public class CheckoutNotificationScheduler {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private IBookingRepo bookingRepo;

    @Autowired
    private IHotelRepo hotelRepo;

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private INotifyService notifyService;

    // Chạy mỗi giờ để kiểm tra các phòng sắp check-out
    @Scheduled(cron = "0 0 * * * *") // 1 giờ = 3600000 ms
    public void checkUpcomingCheckouts() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Lấy thời gian 1 giờ sau
        LocalDateTime oneHourLater = now.plusHours(1);

        // Tìm các booking có checkout time trong khoảng 1 giờ tới
        // Điều chỉnh phương thức này theo cấu trúc thực tế của repository của bạn
        List<Booking> upcomingCheckouts = bookingRepo.findUpcomingCheckouts(now, oneHourLater);

        // Tạo thông báo cho mỗi booking sắp checkout
        for (Booking booking : upcomingCheckouts) {
            if (booking.getRoom() == null || booking.getRoom().getHotel() == null) {
                continue;
            }
            String roomNumber = String.valueOf(booking.getRoom().getId()); // Điều chỉnh theo cấu trúc model của bạn
            String checkoutTime = booking.getCheckOut().format(TIME_FORMATTER); // Điều chỉnh theo cấu trúc model của bạn

            // Lấy hotel ID của booking
            Hotel hotel = booking.getRoom().getHotel(); // Điều chỉnh theo cấu trúc model của bạn

            // Tìm hotel manager của hotel này
            List<User> hotelManagers = userRepo.findManagersByHotelId(hotel.getId());

            // Gửi thông báo cho từng manager
            for (User manager : hotelManagers) {
                notifyService.createCheckoutNotification(roomNumber, checkoutTime, manager.getId());
            }
        }
    }
}