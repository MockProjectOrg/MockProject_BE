package org.example.bookingbe.service.BookingService;

import jakarta.transaction.Transactional;
import org.example.bookingbe.dto.BookingDto;
import org.example.bookingbe.dto.BookingInterface;
import org.example.bookingbe.dto.UserDto;
import org.example.bookingbe.model.*;
import org.example.bookingbe.repository.BookingRepo.IBookingRepo;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.example.bookingbe.repository.StatusRepo.IStatusRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepo bookingRepo;

    @Autowired
    private IRoomRepo roomRepo;

    @Autowired
    private IStatusRepo statusRepo;

    @Autowired
    private IHotelRepo hotelRepo;

    @Autowired
    private IUserRepo userRepo;

    @Transactional
    @Override
    public Booking saveBooking(Long userId, Long roomId, LocalDateTime checkIn, LocalDateTime checkOut, String description) {
        // Kiểm tra phòng có sẵn không
        Optional<Room> roomOpt = roomRepo.findAvailableRoomForBooking(roomId);
        if (roomOpt.isEmpty()) {
            throw new IllegalStateException("Phòng đã được đặt hoặc không khả dụng!");
        }

        Room room = roomOpt.get();

        // Cập nhật trạng thái phòng thành BOOKED
        Status bookedStatus = statusRepo.findByName("BOOKED");
        room.setStatus(bookedStatus);
        roomRepo.save(room);

        // Lưu booking vào DB
        Booking booking = new Booking();
        booking.setUser(new User(userId));
        booking.setRoom(room);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setDescription(description);

        return bookingRepo.save(booking);
    }

    @Transactional
    @Override
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy booking"));

        if (!booking.getUser().getId().equals(userId)) {
            throw new SecurityException("Bạn không có quyền hủy đặt phòng này!");
        }

        if (booking.getCheckIn().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Không thể hủy đặt phòng khi đã đến ngày check-in!");
        }

        Status availableStatus = statusRepo.findByName("AVAILABLE");
        Room room = booking.getRoom();
        room.setStatus(availableStatus);
        roomRepo.save(room);

        bookingRepo.deleteById(bookingId);
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepo.findByUserId(userId);
    }
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepo.findById(id);
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepo.deleteById(id);
    }

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepo.findByUserId(userId);
    }

    @Override
    public List<Booking> getBookingsByHotelManager(Long managerId) {
        return bookingRepo.findBookingsByHotelManager(managerId);
    }

    @Override
    public List<Booking> getBookingsByHotelId(Long hotelId) {
        return bookingRepo.findByHotelId(hotelId);
    }

    @Override
    public boolean isBookingBelongToHotel(Long bookingId, Long hotelId) {
        return bookingRepo.isBookingBelongToHotel(bookingId, hotelId);
    }

    @Override
    public BookingDto getBooking(Long id) {
        BookingInterface projection = bookingRepo.getBooking(id);
        if (projection == null) {
            return null;
        }
        System.out.println(projection.getHotelId());
        System.out.println(projection.getCheckIn());
        System.out.println(projection.getRoomId());
        System.out.println(projection.getUserId());
        Optional<Hotel> hotel = hotelRepo.findById(projection.getHotelId());
        Optional<Room> room = roomRepo.findById(projection.getRoomId());
        UserDto user = userRepo.findUserById(projection.getUserId());
        return new BookingDto(
                hotel,
                room,
                user,
                projection.getCheckIn(),
                projection.getCheckOut(),
                projection.getDescription()
        );
    }
}
