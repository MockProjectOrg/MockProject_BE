package org.example.bookingbe.service.ImageService;

import org.example.bookingbe.repository.ImageRepo.IImageRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private final IImageRepo imageRepo;

    public ImageService(IImageRepo imageRepository) {
        this.imageRepo = imageRepository;
    }

    public List<String> getImagesByRoomId(String roomId) {
        return List.of(
                "https://res.cloudinary.com/demo/image/upload/v1610000000/sample.jpg",
                "https://res.cloudinary.com/demo/image/upload/v1610000001/sample2.jpg"
        );
    }
}
