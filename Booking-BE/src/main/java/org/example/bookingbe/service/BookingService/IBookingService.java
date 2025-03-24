package org.example.bookingbe.service.BookingService;

import jakarta.transaction.Transactional;
import org.example.bookingbe.dto.BookingDto;
import org.example.bookingbe.model.Booking;
import org.example.bookingbe.model.Hotel;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IBookingService {
    List<Booking> getAllBookings();
    Optional<Booking> getBookingById(Long id);
    Booking saveBooking(Booking booking);
    void deleteBooking(Long id);
    List<Booking> getBookingsByUserId(Long userId);
    List<Booking> getBookingsByHotelManager(Long managerId);

    @Transactional
    Booking saveBooking(Long userId, Long roomId, LocalDateTime checkIn, LocalDateTime checkOut, String description);

    void cancelBooking(Long bookingId, Long userId);
    List<Booking> getBookingsByUser(Long userId);

    // Thêm các phương thức mới
    List<Booking> getBookingsByHotelId(Long hotelId);
    boolean isBookingBelongToHotel(Long bookingId, Long hotelId);


    // Tổng số booking bị hủy
    Long countCancelledBookings();

    // Kiểm tra xem user có đặt phòng và đã CHECKED_OUT chưa
    boolean isUserCheckedOut(Long userId, Long roomId);

    // Số lượng phòng trống
    Integer getCountRoomAvailable();

    // Lấy URL biểu đồ doanh thu
    String getSalesChartUrl();

    // Dữ liệu đặt phòng theo tháng
    List<Object[]> getBookingDataByMonth();

    // Danh sách loại phòng phổ biến
    List<Map<String, Object>> getPopularRoomTypes();

}
