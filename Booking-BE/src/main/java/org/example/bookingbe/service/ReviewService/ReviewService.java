package org.example.bookingbe.service.ReviewService;

import org.example.bookingbe.model.Review;
import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.example.bookingbe.repository.ReviewRepo.IReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private IReviewRepo reviewRepo;

    @Autowired
    private IBookingRepo bookingRepo;

    @Override
    public List<Review> getReviewsByRoom(Long roomId) {
        return reviewRepo.findByRoomId(roomId);
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepo.save(review);
    }

    @Override
    public boolean canUserReview(Long userId, Long roomId) {
        return bookingRepo.existsByUserIdAndRoomIdAndCheckOutBefore(userId, roomId, LocalDateTime.now());
    }

    @Override
    public boolean hasCheckedOut(Long userId, Long roomId) {
        return bookingRepo.existsByUserIdAndRoomIdAndCheckOutBefore(userId, roomId, LocalDateTime.now());
    }
}
