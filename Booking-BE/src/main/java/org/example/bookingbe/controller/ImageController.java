package org.example.bookingbe.controller;

import org.example.bookingbe.respone.MessageRespone;
import org.example.bookingbe.service.ImageService.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("roomId") Long roomId) {
        try {
            String fileName = imageService.saveImage(file);
            imageService.saveImageInfo(fileName, roomId);
            return ResponseEntity.ok(new MessageRespone("Image uploaded successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageRespone("Invalid request: " + e.getMessage()));
        }
    }
}
