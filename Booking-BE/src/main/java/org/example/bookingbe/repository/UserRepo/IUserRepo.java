package org.example.bookingbe.repository.UserRepo;

import org.example.bookingbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IUserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.role r JOIN Hotel h ON h.user = u WHERE h.id = :hotelId AND r.roleName = 'HOTEL_MANAGER'")
    List<User> findManagersByHotelId(@Param("hotelId") Long hotelId);

    // Custom query to find VIP users (this is an example, adapt to your actual data model)
    @Query("SELECT u FROM User u WHERE u.role.roleName = 'VIP_USER'")
    List<User> findByVipStatusTrue();

    // Custom query to find frequent users (example implementation)
    @Query(value = "SELECT u.* FROM users u JOIN booking b ON u.id = b.user_id " +
            "GROUP BY u.id HAVING COUNT(b.id) >= 2", nativeQuery = true)
    List<User> findByFrequentBookerTrue();

    List<User> findByCreatedAtAfter(LocalDateTime date);
}
