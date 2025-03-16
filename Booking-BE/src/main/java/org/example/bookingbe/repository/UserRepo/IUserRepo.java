package org.example.bookingbe.repository.UserRepo;

import org.example.bookingbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
