package org.example.bookingbe.service.BookingService;

import jakarta.transaction.Transactional;
import org.example.bookingbe.dto.BookingDto;
import org.example.bookingbe.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IBookingService {

    @Transactional
    Booking saveBooking(Long userId, Long roomId, LocalDateTime checkIn, LocalDateTime checkOut, String description);

    // Danh sách đặt phòng
    List<Booking> getAllBookings();

    // Lấy đặt phòng theo ID
    Optional<Booking> getBookingById(Long id);

    // Lưu đặt phòng
    Booking saveBooking(Booking booking);

    // Xóa đặt phòng
    void deleteBooking(Long id);

    // Lấy danh sách đặt phòng theo User ID
    List<Booking> getBookingsByUserId(Long userId);

    // Lấy danh sách đặt phòng theo Hotel ID
    List<Booking> getBookingsByHotelId(Long hotelId);

    List<Booking> getBookingsByHotelManager(Long managerId);

    // Kiểm tra đặt phòng có thuộc về khách sạn hay không
    boolean isBookingBelongToHotel(Long bookingId, Long hotelId);

    // Hủy đặt phòng
    void cancelBooking(Long bookingId, Long userId);

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

    BookingDto getBooking(Long id);
}
