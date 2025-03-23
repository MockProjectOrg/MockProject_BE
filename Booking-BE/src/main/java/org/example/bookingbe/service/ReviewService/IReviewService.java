package org.example.bookingbe.service.ReviewService;

import org.example.bookingbe.model.Review;

import java.util.List;

public interface IReviewService {
    Review saveReview(Review review);

    List<Review> getReviewsByRoomId(Long roomId);

    boolean canUserReview(Long userId, Long roomId);

    boolean hasCheckedOut(Long id, Long roomId);

    List<Review> getReviewsForCheckedOutBookings();
}
