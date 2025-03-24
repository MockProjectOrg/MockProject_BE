package org.example.bookingbe.repository.BookingRepo;

import org.example.bookingbe.dto.BookingDto;
import org.example.bookingbe.dto.BookingInterface;
import org.example.bookingbe.model.Booking;
import org.example.bookingbe.model.Hotel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IBookingRepo extends JpaRepository<Booking, Long> {
    @EntityGraph(attributePaths = {"user", "room"})
    List<Booking> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "room"})
    List<Booking> findByRoomId(Long roomId);

    @EntityGraph(attributePaths = {"user", "room"})
    @Query("SELECT b FROM Booking b JOIN b.room r JOIN r.hotel h WHERE h.user.id = :managerId")
    List<Booking> findBookingsByHotelManager(@Param("managerId") Long managerId);


    @EntityGraph(attributePaths = {"user", "room", "room.hotel"})
    @Query("SELECT b FROM Booking b JOIN b.room r WHERE r.hotel.id = :hotelId")
    List<Booking> findByHotelId(@Param("hotelId") Long hotelId);

    // Kiểm tra xem một booking có thuộc về một hotel cụ thể không
    @Query("SELECT COUNT(b) > 0 FROM Booking b JOIN b.room r WHERE b.id = :bookingId AND r.hotel.id = :hotelId")
    boolean isBookingBelongToHotel(@Param("bookingId") Long bookingId, @Param("hotelId") Long hotelId);

    @Query("SELECT b FROM Booking b WHERE b.checkOut BETWEEN :startDate AND :endDate")
    List<Booking> findUpcomingCheckouts(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.bookingCancel IS NOT NULL")
    Long countCancelledBookings();

    @Query("SELECT COUNT(b) FROM Booking b")
    Long countTotalOrders();

    @Query("SELECT COUNT(b) FROM Booking b WHERE FUNCTION('MONTH', b.bookingDate) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE)")
    Long countOrdersThisMonth();

    @Query("SELECT COUNT(r) FROM Room r WHERE r.status.statusName = 'AVAILABLE'")
    Long countAvailableRooms();

    @Query("SELECT MONTH(b.bookingDate), COUNT(b) FROM Booking b GROUP BY MONTH(b.bookingDate) ORDER BY MONTH(b.bookingDate)")
    List<Object[]> countBookingsByMonth();

    // Đặt phòng theo trạng thái
    @Query("SELECT b.status, COUNT(b) FROM Booking b GROUP BY b.status")
    List<Object[]> getBookingCountsByStatus();

    // Lấy loại phòng phổ biến
    @Query("SELECT rt.typeName, COUNT(b) FROM Booking b JOIN b.room r JOIN r.roomType rt GROUP BY rt.typeName ORDER BY COUNT(b) DESC")
    List<Object[]> getMostPopularRoomType();

    // tìm đơn đặt phòng theo quản lý khách sạn


    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    BookingInterface getBooking(@Param("id") Long id);

    // kiểm tra check out của người dùng
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.user.id = :userId AND b.room.id = :roomId AND b.checkOut <= CURRENT_TIMESTAMP")
    boolean hasUserCheckedOut(@Param("userId") Long userId, @Param("roomId") Long roomId);

    @Query("SELECT rt.typeName, SUM(r.price) FROM Booking b JOIN b.room r JOIN r.roomType rt GROUP BY rt.typeName")
    List<Object[]> revenueByRoomType();
}
