package org.example.bookingbe.controller;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.service.CloudinaryService.CloudinaryService;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.example.bookingbe.service.RoomService.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/room")
public class RoomController {

    private final IRoomService roomService;
    private final CloudinaryService cloudinaryService;

    public RoomController(RoomService roomService, CloudinaryService cloudinaryService) {
        this.roomService = roomService;
        this.cloudinaryService = cloudinaryService;
    }

    @Transactional
    @GetMapping("/{id}")
    public String getRoomDetail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Room room = roomService.findById(id);
        if (room == null) {
            redirectAttributes.addFlashAttribute("error", "Room not found!");
            return "redirect:/rooms";
        }
        // Bây giờ có thể truy cập room.getHotel().getHotelName() mà không gặp lỗi LazyInitialization
        model.addAttribute("room", room);
        model.addAttribute("images", room.getImages());
        model.addAttribute("review", room.getReviews());
        return "/client/room_detail";
    }

    @GetMapping("/admin/{id}")
    public String getAdminRoomDetail(@PathVariable Long id, Model model) {
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        model.addAttribute("images", room.getImages());
        return "/adminHotel/adminRoom_detail";
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getContentType().startsWith("image/")) {
                return ResponseEntity.status(400).body("Invalid file type. Please upload an image.");
            }
            String imageUrl = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok().body("Image uploaded successfully: " + imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
    }
}
