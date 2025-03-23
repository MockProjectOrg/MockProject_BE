package org.example.bookingbe.service.ReviewService;

import org.example.bookingbe.model.Review;
import org.example.bookingbe.model.Status;
import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.example.bookingbe.repository.ReviewRepo.IReviewRepo;
import org.example.bookingbe.repository.StatusRepo.IStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private IReviewRepo reviewRepo;

    @Autowired
    private IBookingRepo bookingRepo;
    @Autowired
    private IStatusRepo statusRepo;

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
        Status checkedOutStatus = statusRepo.findByStatusName("CHECKED_OUT");
        if (checkedOutStatus == null) {
            return false;
        }
        return bookingRepo.existsByUserIdAndRoomIdAndStatus_StatusName(userId, roomId, checkedOutStatus.getStatusName());
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