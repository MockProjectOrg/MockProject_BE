package org.example.bookingbe.service.RoomService;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.Status;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.example.bookingbe.repository.StatusRepo.IStatusRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IRoomRepo roomRepo;

    @Autowired
    private IHotelRepo hotelRepo;

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IStatusRepo statusRepo;

    @Transactional(readOnly = true)
    @Override
    public Optional<Room> findById(Long id) {
        return roomRepo.findById(id);
    }

    @Override
    public Optional<Room> getRoomByIdWithDetails(Long roomId) {
        return roomRepo.findByIdWithDetails(roomId);
    }

    @Transactional
    public Room getRoomWithUtilities(Long id) {
        return roomRepo.findByIdWithUtilities(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

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


    @Transactional
    public Optional<Room> getRoomById(Long id) {
        return roomRepo.findByIdWithHotelAndUser(id);
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
    public List<Room> searchRooms(String hotelName, String typeName, Double minPrice, Double maxPrice, LocalDateTime checkIn, LocalDateTime checkOut) {
        List<Room> rooms = roomRepo.searchRooms(hotelName, typeName, minPrice, maxPrice);

        if (checkIn != null && checkOut != null) {
            rooms = rooms.stream()
                    .filter(room -> roomRepo.isRoomAvailable(room.getId(), checkIn, checkOut))
                    .collect(Collectors.toList());
        }

        return rooms;
    }

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

