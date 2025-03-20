package org.example.bookingbe.controller;

import org.example.bookingbe.service.CloudinaryService.CloudinaryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/upload-avatar")
public class ImageUploadController {

    private final CloudinaryService cloudinaryService;

    public ImageUploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
        try {
            // ✅ Thêm "folder" khi gọi uploadFile()
            String url = cloudinaryService.uploadFile(file, "user_profiles", "profile_" + userId);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed");
        }
    }

}
