package org.example.bookingbe.repository.RoomRepo;

import jakarta.persistence.LockModeType;
import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IRoomRepo extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r JOIN FETCH r.roomType WHERE r.hotel.id = :hotelId")
    List<Room> findRoomsByHotel(@Param("hotelId") Long hotelId);


    @Query("SELECT r FROM Room r " +
            "JOIN r.hotel h " +
            "JOIN r.roomType rt " +
            "WHERE (:hotelName IS NULL OR LOWER(h.hotelName) LIKE LOWER(CONCAT('%', :hotelName, '%'))) " +
            "AND (:roomTypeName IS NULL OR LOWER(rt.typeName) LIKE LOWER(CONCAT('%', :roomTypeName, '%'))) " +
            "AND (:minPrice IS NULL OR r.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR r.price <= :maxPrice)")
    List<Room> searchRooms(@Param("hotelName") String hotelName,
                           @Param("roomTypeName") String typeName,
                           @Param("minPrice") Double minPrice,
                           @Param("maxPrice") Double maxPrice);

    @Query("SELECT CASE WHEN COUNT(b) = 0 THEN true ELSE false END FROM Booking b WHERE "
            + "b.room.id = :roomId AND "
            + "((b.checkIn <= :checkOut AND b.checkOut >= :checkIn))")
    boolean isRoomAvailable(@Param("roomId") Long roomId,
                            @Param("checkIn") LocalDateTime checkIn,
                            @Param("checkOut") LocalDateTime checkOut);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Room r WHERE r.id = :roomId AND r.status.id = 1")
    Optional<Room> findAvailableRoomForBooking(@Param("roomId") Long roomId);

    @Query("SELECT r FROM Room r WHERE r.status.id = 4")
    List<Room> findAvailableRooms();

    Optional<Room> findById(Long id);
    List<Room> findByHotelIdAndStatusId(Long hotelId, Long statusId);

}
