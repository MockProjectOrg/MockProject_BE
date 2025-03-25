package org.example.bookingbe.controller;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.repository.ReviewRepo.IReviewRepo;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.example.bookingbe.service.ImageService.IImageService;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user/")
public class UserRoomController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IRoomRepo roomRepo;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IReviewRepo reviewRepo;

    @GetMapping("/room/{roomId}")
    public String getRoomDetail(@PathVariable Long roomId, Model model) {
        model.addAttribute("room", roomService.getRoomById(roomId));
        model.addAttribute("images", imageService.findAllImagesByRoomId(roomId));
        return "client/room_detail";
    }
}
