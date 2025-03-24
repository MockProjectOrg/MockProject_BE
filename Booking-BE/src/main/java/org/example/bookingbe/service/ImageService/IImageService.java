package org.example.bookingbe.service.ImageService;

import java.util.List;

public interface IImageService {
    List<String> getImagesByRoomId(Long roomId);
}
