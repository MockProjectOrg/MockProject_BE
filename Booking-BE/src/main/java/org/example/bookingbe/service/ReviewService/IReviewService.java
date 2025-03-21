package org.example.bookingbe.service.ReviewService;

import org.example.bookingbe.model.Review;

import java.util.List;

public interface IReviewService {
    List<Review> getReviewsByRoom(Long roomId);

    Review saveReview(Review review);

    boolean canUserReview(Long userId, Long roomId);

    boolean hasCheckedOut(Long id, Long roomId);
}
