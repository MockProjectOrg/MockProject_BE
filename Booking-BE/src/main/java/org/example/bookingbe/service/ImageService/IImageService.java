package org.example.bookingbe.service.ImageService;

import org.example.bookingbe.model.Image;

import java.util.List;

public interface IImageService {
    List<Image> findAllImagesByRoomId(Long roomId);
}
