package org.example.bookingbe.service.BookingService;

import jakarta.transaction.Transactional;
import org.example.bookingbe.dto.BookingDto;
import org.example.bookingbe.model.Booking;

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

    BookingDto getBooking(Long id);

    // Tổng doanh thu của hệ thống (mặc định là 0.0 nếu không có dữ liệu)
    Double getTotalRevenue(int month, int year);

    // Doanh thu theo từng tháng trong năm (Trả về danh sách rỗng nếu không có dữ liệu)
    List<Map<String, Object>> getMonthlyRevenue();

    // Doanh thu theo từng quý trong năm
    List<Map<String, Object>> getQuarterlyRevenue();

    // Doanh thu theo từng năm
    List<Map<String, Object>> getYearlyRevenue();

    // Doanh thu của tháng trước (Trả về 0.0 nếu không có dữ liệu)
    Double getPreviousMonthlyRevenue(int month, int year);

    // Tổng doanh thu của năm hiện tại
    Double getTotalRevenueThisYear();

    // Tổng doanh thu của năm trước
    Double getTotalRevenueLastYear();

    // Thống kê nhanh doanh thu tháng trước (Có thể gộp với getPreviousMonthlyRevenue nếu muốn)
    Double getPreviousMonthlyRevenue();

    // Các gói đặt phòng phổ biến nhất (Trả về danh sách rỗng nếu không có dữ liệu)
    List<Map<String, Object>> getTopPackages();

    // Số lượng booking theo từng tháng trong năm (Trả về danh sách rỗng nếu không có dữ liệu)
    List<Integer> getBookingCountsByMonth();

    // Số lượng booking theo từng trạng thái (Pending, Confirmed, Completed, Canceled)
    Map<String, Integer> getBookingCountsByStatus();

    // Tổng số booking bị hủy (Mặc định là 0 nếu không có dữ liệu)
    Long countCancelledBookings();

    // Kiểm tra xem user có đặt phòng và đã CHECKED_OUT chưa
    boolean isUserCheckedOut(Long userId, Long roomId);

    // Tổng quan các số liệu thống kê (Tổng hợp dữ liệu quan trọng)
    Map<String, Object> getStatistics();

    // Số lượng phòng trống
    Integer getCountRoomAvailable();

    // Danh sách các gói được chọn nhiều nhất
    List<Map<String, Object>> getTopSelectedPackages();

    // Lấy URL biểu đồ doanh thu (Trả về chuỗi rỗng nếu không có dữ liệu)
    String getSalesChartUrl();

    // Dữ liệu đặt phòng theo tháng (Trả về danh sách rỗng nếu không có dữ liệu)
    List<Object[]> getBookingDataByMonth();

    List<Map<String, Object>> getPopularRoomTypes();
}
