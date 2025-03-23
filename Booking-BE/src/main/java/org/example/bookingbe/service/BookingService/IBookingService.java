package org.example.bookingbe.service.BookingService;

import java.util.List;
import java.util.Map;

public interface IBookingService {

    // 🚀 Tổng doanh thu của hệ thống (mặc định là 0.0 nếu không có dữ liệu)
    Double getTotalRevenue();

    // 🚀 Doanh thu theo từng tháng trong năm (Trả về danh sách rỗng nếu không có dữ liệu)
    List<Map<String, Object>> getMonthlyRevenue();

    // 🚀 Doanh thu theo từng quý trong năm
    List<Map<String, Object>> getQuarterlyRevenue();

    // 🚀 Doanh thu theo từng năm
    List<Map<String, Object>> getYearlyRevenue();

    // 🚀 Doanh thu của tháng trước (Trả về 0.0 nếu không có dữ liệu)
    Double getPreviousMonthlyRevenue(int month, int year);

    // 🚀 Tổng doanh thu của năm hiện tại
    Double getTotalRevenueThisYear();

    // 🚀 Tổng doanh thu của năm trước
    Double getTotalRevenueLastYear();

    // 🔥 Thống kê nhanh doanh thu tháng trước (Có thể gộp với getPreviousMonthlyRevenue nếu muốn)
    Double getPreviousMonthlyRevenue();

    // 🚀 Các gói đặt phòng phổ biến nhất (Trả về danh sách rỗng nếu không có dữ liệu)
    List<Map<String, Object>> getTopPackages();

    // 🚀 Số lượng booking theo từng tháng trong năm (Trả về danh sách rỗng nếu không có dữ liệu)
    List<Integer> getBookingCountsByMonth();

    // 🚀 Số lượng booking theo từng trạng thái (Pending, Confirmed, Completed, Canceled)
    Map<String, Integer> getBookingCountsByStatus();

    // 🚀 Tổng số booking bị hủy (Mặc định là 0 nếu không có dữ liệu)
    Long countCancelledBookings();

    // 🚀 Kiểm tra xem user có đặt phòng và đã CHECKED_OUT chưa
    boolean isUserCheckedOut(Long userId, Long roomId);

    // 🔥 Tổng quan các số liệu thống kê (Tổng hợp dữ liệu quan trọng)
    Map<String, Object> getStatistics();

    // 🚀 Số lượng phòng trống
    Integer getCountRoomAvailable();

    // 🚀 Danh sách các gói được chọn nhiều nhất
    List<Map<String, Object>> getTopSelectedPackages();

    // 🚀 Lấy URL biểu đồ doanh thu (Trả về chuỗi rỗng nếu không có dữ liệu)
    String getSalesChartUrl();

    // 🚀 Dữ liệu đặt phòng theo tháng (Trả về danh sách rỗng nếu không có dữ liệu)
    List<Object[]> getBookingDataByMonth();

    List<Map<String, Object>> getPopularRoomTypes();
}
