package org.example.bookingbe.service.ImageService;

import org.example.bookingbe.model.Image;
import org.example.bookingbe.repository.ImageRepo.IImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements IImageService {
    @Autowired
    private IImageRepo imageRepo;
    @Override
    public List<Image> findAllImagesByRoomId(Long roomId) {
        return imageRepo.findByRoomId(roomId);
    }
}
