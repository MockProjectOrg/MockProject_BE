package org.example.bookingbe.repository.BookingRepo;

import org.example.bookingbe.dto.BookingInterface;
import org.example.bookingbe.model.Booking;
import org.springframework.data.domain.Pageable;
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
    List<Booking> findUpcomingCheckouts(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT b.id as id, b.check_in, b.check_out, b.description, b.room_id as roomId , b.user_id as userId, h.id as hotelId FROM Booking AS b  join Room as r on b.room_id = r.id join Hotel AS h on r.hotel_id = h.id where r.id = :id", nativeQuery = true)
    BookingInterface getBooking(Long id);

    @Query("SELECT COUNT(b) FROM Booking b")
    Long countTotalOrders();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.bookingDate BETWEEN :startOfWeek AND :endOfWeek")
    Long countOrdersThisWeek(@Param("startOfWeek") LocalDateTime startOfWeek, @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("SELECT COUNT(b) FROM Booking b WHERE FUNCTION('MONTH', b.bookingDate) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE)")
    Long countOrdersThisMonth();

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b WHERE b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL")
    Double countTotalRevenue();

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('MONTH', b.bookingDate) = :month AND FUNCTION('YEAR', b.bookingDate) = :year " +
            "AND b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL")
    Double getPreviousMonthlyRevenue(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('MONTH', b.bookingDate) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL")
    Double getRevenueThisMonth();

    @Query("SELECT FUNCTION('MONTH', b.bookingDate), COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL " +
            "GROUP BY FUNCTION('MONTH', b.bookingDate) " +
            "ORDER BY FUNCTION('MONTH', b.bookingDate)")
    List<Object[]> getMonthlyRevenue();

    @Query("SELECT FUNCTION('QUARTER', b.bookingDate), COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL " +
            "GROUP BY FUNCTION('QUARTER', b.bookingDate) " +
            "ORDER BY FUNCTION('QUARTER', b.bookingDate)")
    List<Object[]> getQuarterlyRevenue();

    @Query("SELECT FUNCTION('YEAR', b.bookingDate), COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL " +
            "GROUP BY FUNCTION('YEAR', b.bookingDate) " +
            "ORDER BY FUNCTION('YEAR', b.bookingDate)")
    List<Object[]> getYearlyRevenue();

    @Query("SELECT b.room.roomType, COUNT(b) FROM Booking b " +
            "GROUP BY b.room.roomType " +
            "ORDER BY COUNT(b) DESC")
    List<Object[]> getMostPopularRoomType();

    @Query("SELECT b.room.hotel.hotelName, b.room.roomType.typeName, COUNT(b) " +
            "FROM Booking b WHERE b.status.statusName = 'CHECKED_OUT' " +
            "AND b.bookingCancel IS NULL " +
            "GROUP BY b.room.hotel.hotelName, b.room.roomType.typeName " +
            "ORDER BY COUNT(b) DESC")
    List<Object[]> getMostBookedPackages();

    @Query("SELECT FUNCTION('MONTH', b.bookingDate), COUNT(b) FROM Booking b GROUP BY FUNCTION('MONTH', b.bookingDate) ORDER BY FUNCTION('MONTH', b.bookingDate)")
    List<Object[]> getBookingCountsByMonth();

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.room.id = :roomId ORDER BY b.bookingDate DESC")
    List<Booking> findTopByUserIdAndRoomIdOrderByBookingDateDesc(@Param("userId") Long userId, @Param("roomId") Long roomId, Pageable pageable);

    @Query("SELECT b.status.statusName, COUNT(b) FROM Booking b GROUP BY b.status.statusName")
    List<Object[]> getBookingCountsByStatus();


    @Query("SELECT COUNT(b) FROM Booking b WHERE b.bookingCancel IS NOT NULL")
    Long countCancelledBookings();

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('YEAR', b.bookingDate) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL")
    Double getTotalRevenueThisYear();

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('YEAR', b.bookingDate) = :year " +
            "AND b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL")
    Double getTotalRevenueLastYear(@Param("year") int year);

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('MONTH', b.bookingDate) = :month AND FUNCTION('YEAR', b.bookingDate) = :year " +
            "AND b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL")
    Object getMonthlyRevenueByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b " +
            "WHERE FUNCTION('YEAR', b.bookingDate) = :year " +
            "AND b.status.statusName = 'CHECKED_OUT' AND b.bookingCancel IS NULL")
    Object getTotalRevenueByYear(@Param("year") int currentYear);

    boolean existsByUserIdAndRoomIdAndStatus_StatusName(Long userId, Long roomId, String statusName);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.status.statusName = 'AVAILABLE' ")
    Long countAvailableRooms();

    @Query("SELECT b.bookingPackage.packageName, COUNT(b) FROM Booking b " +
            "WHERE b.status.statusName = 'CHECKED_OUT' " +
            "GROUP BY b.bookingPackage.packageName " +
            "ORDER BY COUNT(b) DESC")
    List<Object[]> getMostSelectedPackages();

    @Query("SELECT MONTH(b.bookingDate), COUNT(b) FROM Booking b GROUP BY MONTH(b.bookingDate)")
    List<Object[]> countBookingsByMonth();
}

