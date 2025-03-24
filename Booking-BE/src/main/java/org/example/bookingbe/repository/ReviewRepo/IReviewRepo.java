package org.example.bookingbe.repository.ReviewRepo;

import org.example.bookingbe.model.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepo extends JpaRepository<Review, Long> {
    void deleteByRoomId(Long roomId);

    @EntityGraph(attributePaths = {"user"})
    List<Review> findByRoomId(Long roomId);

    @Query("SELECT r FROM Review r WHERE r.booking.status IS TRUE")
    List<Review> findReviewsForCheckedOutBookings();

}