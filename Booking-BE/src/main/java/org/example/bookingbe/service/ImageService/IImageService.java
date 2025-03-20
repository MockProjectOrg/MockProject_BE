package org.example.bookingbe.service.ImageService;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    String saveImage(MultipartFile file);

    void saveImageInfo(String fileName, Long roomId);
}
