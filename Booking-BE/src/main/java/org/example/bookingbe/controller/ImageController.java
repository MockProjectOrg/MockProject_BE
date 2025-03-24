package org.example.bookingbe.controller;

import org.example.bookingbe.service.CloudinaryService.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/room/{roomId}")
    public String getRoomImages(@PathVariable String roomId, Model model) {
        try {
            // Giả sử cloudinaryService.getImageUrl trả về một danh sách các URL ảnh cho phòng
            List<String> images = cloudinaryService.getImageUrlsForRoom(roomId);
            model.addAttribute("images", images);

            return "client/room_detail";  // Trả về trang chi tiết phòng
        } catch (Exception e) {
            // Ghi log và trả về trang lỗi nếu gặp sự cố
            model.addAttribute("error", "Unable to fetch room images. Please try again later.");
            return "error";  // Trang lỗi
        }
    }
}