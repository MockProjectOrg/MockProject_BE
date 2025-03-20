package org.example.bookingbe.controller;

import org.example.bookingbe.service.CloudinaryService.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/room/{roomId}")
    public String getRoomImages(@PathVariable String roomId, Model model) {
        try {
            // Lấy danh sách ảnh từ Cloudinary
            List<String> images = Collections.singletonList(String.valueOf(cloudinaryService.getImageUrl(roomId)));

            // Debug kiểm tra danh sách ảnh
            System.out.println("DEBUG: Danh sách ảnh của phòng " + roomId + " -> " + images);

            // Thêm danh sách ảnh vào Model để Thymeleaf hiển thị
            model.addAttribute("images", images);

            // Trả về template Thymeleaf "room_detail.html"
            return "room_detail";
        } catch (IOException e) {
            model.addAttribute("error", "Lỗi tải ảnh: " + e.getMessage());
            return "error";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
