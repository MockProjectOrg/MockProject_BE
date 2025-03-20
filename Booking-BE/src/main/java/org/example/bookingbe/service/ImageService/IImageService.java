package org.example.bookingbe.service.ImageService;

import org.example.bookingbe.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    String saveImage(MultipartFile file) throws IOException;

    void saveImageInfo(String imageName, Long roomId);

    String getImageUrl(String imageName);

    void deleteImage(String imageName);

    List<Image> getAllImageByRoomId(Long roomId);

    String getImageUrl(String imageName, int width, int height);

    String uploadImage(MultipartFile file, String folder) throws IOException;

    List<String> getImagesByRoomId(String roomId);


}
