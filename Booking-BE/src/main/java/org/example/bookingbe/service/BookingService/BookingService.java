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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    public BookingService(IBookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public Double getTotalRevenue() {
        return Optional.ofNullable(bookingRepo.countTotalRevenue()).orElse(0.0);
    }

    private Long countOrdersThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfWeek = today.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endOfWeek = today.with(DayOfWeek.SUNDAY).plusDays(1).atStartOfDay().minusSeconds(1);
        return Optional.ofNullable(bookingRepo.countOrdersThisWeek(startOfWeek, endOfWeek)).orElse(0L);
    }

    @Override
    public List<Map<String, Object>> getMonthlyRevenue() {
        return Optional.ofNullable(bookingRepo.getMonthlyRevenue())
                .orElse(Collections.emptyList())
                .stream().map(row -> {
                    Map<String, Object> revenueMap = new HashMap<>();
                    revenueMap.put("month", ((Number) row[0]).intValue());
                    revenueMap.put("revenue", ((Number) row[1]).doubleValue());
                    return revenueMap;
                }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getQuarterlyRevenue() {
        return Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> getYearlyRevenue() {
        return Collections.emptyList();
    }

    @Override
    public Double getPreviousMonthlyRevenue(int month, int year) {
        return 0.0;
    }

    @Override
    public Double getTotalRevenueThisYear() {
        return 0.0;
    }

    @Override
    public Double getTotalRevenueLastYear() {
        return 0.0;
    }

    @Override
    public Double getPreviousMonthlyRevenue() {
        return 0.0;
    }

    @Override
    public List<Map<String, Object>> getTopPackages() {
        return Collections.emptyList();
    }

    @Override
    public Map<String, Integer> getBookingCountsByStatus() {
        return Optional.ofNullable(bookingRepo.getBookingCountsByStatus())
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(
                        row -> row[0] != null ? row[0].toString() : "UNKNOWN",
                        row -> row[1] instanceof Number ? ((Number) row[1]).intValue() : 0
                ));
    }

    @Override
    public Long countCancelledBookings() {
        return Optional.ofNullable(bookingRepo.countCancelledBookings()).orElse(0L);
    }

    @Override
    public boolean isUserCheckedOut(Long userId, Long roomId) {
        return false;
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalOrders", Optional.ofNullable(bookingRepo.countTotalOrders()).orElse(0L));
        statistics.put("thisWeekOrders", countOrdersThisWeek());
        statistics.put("thisMonthlyOrders", Optional.ofNullable(bookingRepo.countOrdersThisMonth()).orElse(0L));
        statistics.put("totalRevenues", getTotalRevenue());
        statistics.put("thisMonthlyRevenues", Optional.ofNullable(bookingRepo.getRevenueThisMonth()).orElse(0.0));
        return statistics;
    }

    @Override
    public Integer getCountRoomAvailable() {
        return Math.toIntExact(Optional.ofNullable(bookingRepo.countAvailableRooms()).orElse(0L));
    }

    @Override
    public List<Map<String, Object>> getTopSelectedPackages() {
        return Collections.emptyList();
    }

    @Override
    public String getSalesChartUrl() {
        return "";
    }

    @Override
    public List<Object[]> getBookingDataByMonth() {
        return Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> getPopularRoomTypes() {
        List<Object[]> results = Optional.ofNullable(bookingRepo.getMostPopularRoomType())
                .orElse(Collections.emptyList());

        return results.stream().map(row -> {
            Map<String, Object> roomData = new HashMap<>();
            roomData.put("roomType", row[0]);  // Tên loại phòng
            roomData.put("bookingCount", ((Number) row[1]).intValue()); // Số lượt đặt
            return roomData;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getBookingCountsByMonth() {
        return Optional.ofNullable(bookingRepo.getBookingCountsByMonth())
                .orElse(Collections.emptyList())
                .stream()
                .map(row -> row != null && row.length > 1 && row[1] instanceof Number ? ((Number) row[1]).intValue() : 0)
                .collect(Collectors.toList());
    }

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
