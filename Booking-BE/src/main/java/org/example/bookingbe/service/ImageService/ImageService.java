package org.example.bookingbe.service.ImageService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService implements IImageService {
    @Override
    public String saveImage(MultipartFile file) {
        return "";
    }

    @Override
    public void saveImageInfo(String fileName, Long roomId) {

    }
}
