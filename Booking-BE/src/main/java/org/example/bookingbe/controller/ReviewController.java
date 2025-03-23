package org.example.bookingbe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.bookingbe.model.Review;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.User;
import org.example.bookingbe.service.BookingService.BookingService;
import org.example.bookingbe.service.ReviewService.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BookingService bookingService;

    // Lấy danh sách các review của phòng
    @GetMapping("/room/{roomId}")
    public String getRoomDetails(@PathVariable Long roomId, Model model, HttpSession session) {
        // Lấy danh sách review của phòng
        List<Review> reviews = reviewService.getReviewsByRoomId(roomId);
        model.addAttribute("reviews", reviews);

        // Lấy thông tin user từ session
        User user = (User) session.getAttribute("loggedUser");
        boolean isCheckedOut = false;

        if (user != null) {
            isCheckedOut = reviewService.hasCheckedOut(user.getId(), roomId);
        }

        model.addAttribute("isCheckedOut", isCheckedOut);
        return "client/room_detail";
    }

    // Lưu review từ form
    @PostMapping("/submit")
    public String submitReview(@RequestParam Long roomId,
                               @RequestParam int rating,
                               @RequestParam String comment,
                               HttpSession session) {

        // Kiểm tra nếu người dùng chưa đăng nhập
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            session.setAttribute("errorMessage", "Bạn cần đăng nhập trước khi gửi review.");
            return "redirect:/api/login"; // Chuyển hướng đến trang đăng nhập
        }

        // Kiểm tra xem người dùng đã checkout chưa
        boolean hasCheckedOut = reviewService.hasCheckedOut(user.getId(), roomId);
        if (!hasCheckedOut) {
            session.setAttribute("errorMessage", "Bạn cần checkout phòng này trước khi gửi review.");
            return "redirect:/rooms/" + roomId; // Nếu chưa checkout, không thể gửi review
        }

        // Kiểm tra rating và comment hợp lệ
        if (rating < 1 || rating > 5) {
            session.setAttribute("errorMessage", "Đánh giá phải từ 1 đến 5.");
            return "redirect:/rooms/" + roomId;
        }

        if (comment == null || comment.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Vui lòng cung cấp mô tả cho review.");
            return "redirect:/rooms/" + roomId;
        }

        // Tạo đối tượng review và lưu vào database
        Review review = new Review();
        review.setRoom(new Room(roomId));  // Gán phòng cho review
        review.setUser(user);  // Gán người dùng cho review
        review.setRate(rating);  // Gán đánh giá cho review
        review.setDescription(comment);  // Gán mô tả cho review

        reviewService.saveReview(review);
        return "redirect:/rooms/" + roomId;
    }

    @GetMapping("/checked-out")
    public ResponseEntity<List<Review>> getCheckedOutReviews() {
        List<Review> reviews = reviewService.getReviewsForCheckedOutBookings();
        return ResponseEntity.ok(reviews);
    }
}