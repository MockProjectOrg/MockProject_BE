package org.example.bookingbe.service.ImageService;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.repository.ImageRepo.IImageRepo;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.bookingbe.model.Image;
import org.example.bookingbe.repository.ImageRepo.IImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ImageService implements IImageService {
    @Autowired
    private IImageRepo imageRepo;

    @Autowired
    private IRoomRepo roomRepo;

    @Override
    public List<Image> findAllImagesByRoomId(Long roomId) {
        return imageRepo.findByRoomId(roomId);
    }

    @Override
    public List<String> getImagesByRoomId(Long roomId) {
        Room room = roomRepo.findById(roomId).orElse(null);
        if (room == null) return List.of();  // Trả về danh sách rỗng nếu không tìm thấy phòng

        return imageRepo.findImagesByRoom(room);
    }
}
