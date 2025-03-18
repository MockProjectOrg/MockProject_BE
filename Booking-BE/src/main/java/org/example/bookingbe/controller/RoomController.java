package org.example.bookingbe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.Status;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.HotelRepo.IHotelRepo;
import org.example.bookingbe.repository.UserRepo.IUserRepo;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.example.bookingbe.service.UserDetail.CustomUserDetailService;
import org.example.bookingbe.service.UserDetail.UserPriciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/managerRooms")
public class RoomController {
    @Autowired
    private IRoomService roomService;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private HttpSession session;

    @Autowired
    private IHotelRepo hotelRepo;

    @Autowired
    private IUserRepo userRepo;

    // Danh sách phòng của khách sạn do Manager quản lý
    @GetMapping
    public String listRooms(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int pageSize, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPriciple userPriciple = (UserPriciple) authentication.getPrincipal();

        Long userId = userPriciple.getId();
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Hotel hotel = hotelRepo.findByUser(user).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No hotel found for this user"));

        session.setAttribute("hotelId", hotel.getId());

        List<Room> rooms = roomService.getRoomsByHotel(hotel.getId(), userId);

        // Lấy danh sách tất cả trạng thái phòng để hiển thị
        List<Status> allStatuses = roomService.getAllStatuses();

        model.addAttribute("rooms", rooms);
        model.addAttribute("hotel", hotel);
        model.addAttribute("allStatuses", allStatuses);
        model.addAttribute("newRoom", new Room());
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalRooms", rooms.size());

        return "managerHotel/managerRoom";
    }


    // Thêm phòng mới
    @PostMapping("/add")
    public String addRoom(@ModelAttribute Room room) {
        // Lấy thông tin người dùng từ Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPriciple userPriciple = (UserPriciple) authentication.getPrincipal();

        Long userId = userPriciple.getId();
        Long hotelId = (Long) session.getAttribute("hotelId"); // Lấy hotelId từ session

        System.out.println("User ID: " + userId);
        System.out.println("Hotel ID: " + hotelId);

        if (hotelId == null) {
            throw new RuntimeException("Chỉ có Manager mới có thể thêm phòng");
        }

        // Kiểm tra vai trò của người dùng
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        System.out.println("User Role: " + user.getRole().getRoleName());

        if (!"HOTEL MANAGER".equalsIgnoreCase(user.getRole().getRoleName())) {
            throw new RuntimeException("Người dùng không có quyền thêm phòng");
        }

        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách sạn"));

        room.setHotel(hotel);
        roomService.createRoom(room, userId);

        return "redirect:/managerRooms";
    }


    //Chỉnh sửa phòng
    // Hiển thị trang chỉnh sửa phòng
    @GetMapping("/edit/{id}")
    public String editRoom(@PathVariable Long id, Model model) {
        // Lấy thông tin user từ Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPriciple userPriciple = (UserPriciple) authentication.getPrincipal();
        Long userId = userPriciple.getId();

        Room room = roomService.getRoomById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng"));

        // Kiểm tra quyền sở hữu
        if (!room.getHotel().getUser().getId().equals(userId)) {
            throw new RuntimeException("Bạn không có quyền chỉnh sửa phòng này");
        }

        model.addAttribute("room", room);
        return "managerHotel/editRoom";  // Tạo file `editRoom.html` trong thư mục Thymeleaf
    }

    // Xử lý cập nhật phòng
    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room updatedRoom) {
        // Lấy thông tin user từ Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPriciple userPriciple = (UserPriciple) authentication.getPrincipal();
        Long userId = userPriciple.getId();

        roomService.updateRoom(id, updatedRoom, userId);
        return "redirect:/managerRooms";
    }

    // Xóa phòng
    @GetMapping("/delete/{id}")
    public String deleteRoom(@RequestParam Long userId, @PathVariable Long id) {
        roomService.deleteRoom(id, userId);
        return "redirect:/managerRooms?userId=" + userId;
    }

    @PostMapping("/updateStatus/{id}")
    public String updateRoomStatus(@PathVariable Long id, @RequestParam Long statusId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPriciple userPriciple = (UserPriciple) authentication.getPrincipal();
        Long userId = userPriciple.getId();

        roomService.updateRoomStatus(id, statusId, userId);
        return "redirect:/managerRooms";
    }
}

