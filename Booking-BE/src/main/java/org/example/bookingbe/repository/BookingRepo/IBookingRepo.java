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
    List<Booking> findUpcomingCheckouts(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);


}
