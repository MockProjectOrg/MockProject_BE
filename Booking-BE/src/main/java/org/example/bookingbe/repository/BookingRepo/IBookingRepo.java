package org.example.bookingbe.repository.BookingRepo;

import org.example.bookingbe.model.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IBookingRepo extends JpaRepository<Booking, Long> {

    // Tổng số lượt đặt phòng
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.bookingCancel IS NULL")
    Double countTotalBookings();

    // Tổng doanh thu từ các booking hoàn thành
    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b WHERE b.status.statusName = 'COMPLETED' AND b.bookingCancel IS NULL")
    Double getTotalRevenue();

    // Doanh thu trong tháng hiện tại
    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('MONTH', b.bookingDate) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = 'COMPLETED' AND b.bookingCancel IS NULL")
    Double getRevenueThisMonth();

    // Doanh thu trong tuần hiện tại
    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE b.bookingDate BETWEEN :startOfWeek AND :endOfWeek " +
            "AND b.status.statusName = 'COMPLETED' AND b.bookingCancel IS NULL")
    Double getRevenueCurrentWeek();

    // Doanh thu theo tháng của năm hiện tại
    @Query("SELECT FUNCTION('MONTH', b.bookingDate), COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = 'COMPLETED' AND b.bookingCancel IS NULL " +
            "GROUP BY FUNCTION('MONTH', b.bookingDate) " +
            "ORDER BY FUNCTION('MONTH', b.bookingDate)")
    List<Object[]> getMonthlyRevenue();

    // Doanh thu theo quý của năm hiện tại
    @Query("SELECT FUNCTION('QUARTER', b.bookingDate), COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = 'COMPLETED' AND b.bookingCancel IS NULL " +
            "GROUP BY FUNCTION('QUARTER', b.bookingDate) " +
            "ORDER BY FUNCTION('QUARTER', b.bookingDate)")
    List<Object[]> getQuarterlyRevenue();

    // Doanh thu theo năm
    @Query("SELECT FUNCTION('YEAR', b.bookingDate), COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE b.status.statusName = 'COMPLETED' AND b.bookingCancel IS NULL " +
            "GROUP BY FUNCTION('YEAR', b.bookingDate) " +
            "ORDER BY FUNCTION('YEAR', b.bookingDate)")
    List<Object[]> getYearlyRevenue();

    // Loại phòng phổ biến nhất
    @Query("SELECT b.room.roomType FROM Booking b " +
            "GROUP BY b.room.roomType " +
            "ORDER BY COUNT(b) DESC")
    List<String> getMostPopularRoomType(Pageable pageable);

    // Gói đặt phòng phổ biến nhất
    @Query("SELECT b.room.roomType, COUNT(b) FROM Booking b " +
            "GROUP BY b.room.roomType " +
            "ORDER BY COUNT(b) DESC")
    List<Object[]> getTopPackages();

    // Thống kê tổng hợp
    @Query("SELECT new map(" +
            "(SELECT COUNT(b) FROM Booking b WHERE b.bookingCancel IS NULL) AS totalBookings, " +
            "(SELECT COUNT(b) FROM Booking b WHERE b.bookingCancel IS NOT NULL) AS canceledBookings, " +
            "(SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b WHERE b.status.statusName = 'COMPLETED' AND b.bookingCancel IS NULL) AS totalRevenue, " +
            "(SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b WHERE b.bookingDate = CURRENT_DATE AND b.status.statusName = 'COMPLETED' AND b.bookingCancel IS NULL) AS revenueToday " +
            ")")
    Map<String, Object> getStatistics();
}
