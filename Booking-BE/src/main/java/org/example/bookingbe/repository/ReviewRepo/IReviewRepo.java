package org.example.bookingbe.repository.ReviewRepo;

import org.example.bookingbe.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findByRoomId(Long roomId);
}
