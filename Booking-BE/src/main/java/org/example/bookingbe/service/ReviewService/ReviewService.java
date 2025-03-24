package org.example.bookingbe.service.ReviewService;

import org.example.bookingbe.model.Review;
import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.example.bookingbe.repository.ReviewRepo.IReviewRepo;
import org.example.bookingbe.repository.StatusRepo.IStatusRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService implements IReviewService {

    private final IReviewRepo reviewRepo;
    private final IBookingRepo bookingRepo;
    private final IStatusRepo statusRepo;

    // Dùng Constructor Injection
    public ReviewService(IReviewRepo reviewRepo, IBookingRepo bookingRepo, IStatusRepo statusRepo) {
        this.reviewRepo = reviewRepo;
        this.bookingRepo = bookingRepo;
        this.statusRepo = statusRepo;
    }

    @Transactional
    @Override
    public Review saveReview(Review review) {
        return reviewRepo.save(review);
    }

    @Override
    public List<Review> getReviewsByRoomId(Long roomId) {
        return reviewRepo.findByRoomId(roomId);
    }

    @Override
    public boolean hasCheckedOut(Long userId, Long roomId) {
        return bookingRepo.hasUserCheckedOut(userId, roomId);
    }

    @Override
    public boolean canUserReview(Long userId, Long roomId) {
        return hasCheckedOut(userId, roomId);
    }

    @Override
    public List<Review> getReviewsForCheckedOutBookings() {
        return reviewRepo.findReviewsForCheckedOutBookings();
    }
}
