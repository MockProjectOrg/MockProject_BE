package org.example.bookingbe.service.ImageService;

import java.util.List;

import org.example.bookingbe.model.Image;

import java.util.List;

public interface IImageService {
    List<String> getImagesByRoomId(Long roomId);
    List<Image> findAllImagesByRoomId(Long roomId);
}
