package org.example.bookingbe.repository.BookingRepo;

import org.example.bookingbe.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IBookingRepo extends JpaRepository<Booking, Long> {

    @Query("SELECT COUNT(b) FROM Booking b")
    long countTotalBookings();

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b WHERE b.status.statusName = :statusName AND b.bookingCancel IS NULL")
    Double getTotalRevenue(@Param("statusName") String statusName);

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('MONTH', b.bookingDate) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = :statusName AND b.bookingCancel IS NULL")
    Double getRevenueThisMonth(@Param("statusName") String statusName);

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE b.bookingDate BETWEEN :startOfWeek AND :endOfWeek " +
            "AND b.status.statusName = :statusName AND b.bookingCancel IS NULL")
    Double getRevenueThisWeek(@Param("statusName") String statusName,
                              @Param("startOfWeek") LocalDate startOfWeek,
                              @Param("endOfWeek") LocalDate endOfWeek);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.bookingCancel IS NOT NULL")
    long countCanceledBookings();

    // 🔹 Lấy loại phòng được đặt nhiều nhất
    @Query("SELECT b.room.roomType FROM Booking b " +
            "GROUP BY b.room.roomType " +
            "ORDER BY COUNT(b) DESC LIMIT 1")
    String getMostPopularRoomType();

    // 🔹 Lấy doanh thu theo năm
    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('YEAR', b.bookingDate) = :year " +
            "AND b.status.statusName = 'COMPLETED' " +
            "AND b.bookingCancel IS NULL")
    Double getRevenueByYear(@Param("year") int year);

    // 🔹 Lấy doanh thu theo quý
    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('QUARTER', b.bookingDate) = :quarter " +
            "AND FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = 'COMPLETED' " +
            "AND b.bookingCancel IS NULL")
    Double getRevenueByQuarter(@Param("quarter") int quarter);

    // 🔹 Lấy doanh thu theo tháng
    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('MONTH', b.bookingDate) = :month " +
            "AND FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = 'COMPLETED' " +
            "AND b.bookingCancel IS NULL")
    Double getRevenueByMonth(@Param("month") int month);
}