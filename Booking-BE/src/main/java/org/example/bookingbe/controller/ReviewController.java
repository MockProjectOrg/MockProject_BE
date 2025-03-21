package org.example.bookingbe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.bookingbe.model.Review;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.User;
import org.example.bookingbe.service.ReviewService.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{roomId}")
    public String getReviews(@PathVariable Long roomId, Model model, HttpSession session) {
        List<Review> reviews = reviewService.getReviewsByRoom(roomId);

        // Thêm danh sách review vào model
        model.addAttribute("reviews", reviews);

        // Kiểm tra người dùng có đăng nhập không
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("user", user);

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
            return "redirect:/api/login?error=user_not_logged_in"; // Chuyển hướng đến login
        }

        // Kiểm tra xem người dùng đã checkout chưa
        boolean hasCheckedOut = reviewService.hasCheckedOut(user.getId(), roomId);
        if (!hasCheckedOut) {
            return "redirect:/rooms/" + roomId + "?error=not_checked_out";
        }

        // Lưu review nếu đã checkout
        Review review = new Review();
        review.setRoom(new Room(roomId));
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);

        reviewService.saveReview(review);
        return "redirect:/rooms/" + roomId;
    }
}
