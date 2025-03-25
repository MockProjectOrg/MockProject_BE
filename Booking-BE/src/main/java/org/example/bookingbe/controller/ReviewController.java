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


}