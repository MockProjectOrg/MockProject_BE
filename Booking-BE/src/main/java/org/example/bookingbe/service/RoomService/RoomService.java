package org.example.bookingbe.service.RoomService;

import jakarta.transaction.Transactional;
import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.Status;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.ImageRepo.IImageRepo;
import org.example.bookingbe.repository.ReviewRepo.IReviewRepo;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.example.bookingbe.repository.StatusRepo.IStatusRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IRoomRepo roomRepo;

    @Autowired
    private IHotelRepo hotelRepo;

    @Autowired
    private IUserRepo userRepo;


    @Override
    public List<Room> getAvailableRooms() {
        return roomRepo.findAvailableRooms();
    }


    public List<Room> getRoomsByHotel(Long hotelId, Long userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            List<Hotel> hotels = hotelRepo.findByUser(user.get());
            boolean isAuthorized = hotels.stream().anyMatch(hotel -> hotel.getId().equals(hotelId));
            if (isAuthorized) {
                return roomRepo.findRoomsByHotel(hotelId);
            }
        }
        throw new RuntimeException("User is not authorized to access this hotel's rooms");
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return roomRepo.findById(roomId);
    }


    @Override
    public Room createRoom(Room room, Long userId) {
        if (room.getHotel() == null || room.getHotel().getId() == null) {
            throw new IllegalArgumentException("Hotel ID cannot be null");
        }

        Hotel hotel = hotelRepo.findById(room.getHotel().getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        if (!hotel.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You cannot add rooms to this hotel");
        }

        room.setHotel(hotel); // Đảm bảo Room có tham chiếu tới Hotel
        return roomRepo.save(room);
    }


    @Override
    public Room updateRoom(Long roomId, Room updatedRoom, Long userId) {
        Room existingRoom = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!existingRoom.getHotel().getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You cannot update this room");
        }

        existingRoom.setPrice(updatedRoom.getPrice());
        existingRoom.setDescription(updatedRoom.getDescription());
        existingRoom.setRoomType(updatedRoom.getRoomType());

        return roomRepo.save(existingRoom);
    }

    @Autowired
    private IStatusRepo statusRepo; // Inject repository của Status

    @Autowired
    private IReviewRepo reviewRepo;
    @Autowired
    private IImageRepo imageRepo;

    @Override
    @Transactional
    public void deleteRoom(Long roomId, Long userId) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!room.getHotel().getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You cannot delete this room");
        }


        // Xóa phòng sau khi đã xóa dữ liệu liên quan
        roomRepo.deleteById(roomId);
    }

    @Override
    public Room updateRoomStatus(Long roomId, Long statusId, Long userId) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!room.getHotel().getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You cannot update this room's status");
        }

        room.setStatus(statusRepo.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Status not found")));

        return roomRepo.save(room);
    }

    @Override
    public List<Status> getAllStatuses() {
        return statusRepo.findAll(); // Lấy toàn bộ danh sách trạng thái từ database
    }

    @Override
    public List<Room> getAvailableRoomsByHotel(Long hotelId) {
        return roomRepo.findByHotelIdAndStatusId(hotelId, 4L);
    }

}
