package org.example.bookingbe.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.User;
import org.example.bookingbe.repository.RoomRepo.IRoomRepo;
import org.example.bookingbe.respone.MessageRespone;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.example.bookingbe.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IRoomRepo roomRepo;

    @GetMapping("/")
    public String login(HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
            return "redirect:/api/user/home";
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model, HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
            return "redirect:/api/user/home";
        }
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/Doregister")
    public String doRegister(@ModelAttribute("user") @Valid User user, BindingResult result, Model model, HttpSession session) throws MessagingException {
        if (userService.existsUser(user.getUserName()) || userService.exstsEmail(user.getEmail())) {
            model.addAttribute("message", new MessageRespone("User or Email already exists"));
            return "auth/register";
        }
        if (result.hasErrors()) {
            return "auth/register";
        }
        userService.registerUser(user);
        session.setAttribute("userId", user.getId()); // Lưu userId vào session
        return "redirect:/api/login";
    }

    @GetMapping("/user/userProfile")
    public String getUser(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/api/login";
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/api/login";
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/user/updateProfile")
    @ResponseBody
    public ResponseEntity<?> updateProfile(@RequestBody User updatedUser, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.badRequest().body(new MessageRespone("Bạn chưa đăng nhập!"));
        }

        User existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            return ResponseEntity.badRequest().body(new MessageRespone("Không tìm thấy người dùng!"));
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAddress(updatedUser.getAddress());
        // Không cập nhật email trực tiếp để tránh giả mạo danh tính

        userService.saveUser(existingUser);
        return ResponseEntity.ok(new MessageRespone("Cập nhật thông tin thành công!"));
    }

    @GetMapping("/user/searchRooms")
    public String searchAvailableRooms(
            @RequestParam(required = false) String hotelName,
            @RequestParam(required = false) String typeName,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkIn,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOut,
            Model model) {

        List<Room> rooms;

        if (hotelName != null || typeName != null || minPrice != null || maxPrice != null || checkIn != null || checkOut != null) {
            rooms = roomService.searchRooms(hotelName, typeName, minPrice, maxPrice, checkIn, checkOut)
                    .stream()
                    .filter(room -> "AVAILABLE".equals(room.getStatus().getStatusName()))
                    .collect(Collectors.toList());
        } else {
            rooms = roomService.getAvailableRooms();
        }

        if (checkIn != null && checkOut != null) {
            rooms = rooms.stream()
                    .filter(room -> roomRepo.isRoomAvailable(room.getId(), checkIn, checkOut))
                    .collect(Collectors.toList());
        }

        model.addAttribute("rooms", rooms);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        return "client/searchRooms";
    }

    @PostMapping("/upload-avatar")
    public String uploadAvatar(@RequestParam("avatarFile") MultipartFile file, @RequestParam("userId") Long userId, RedirectAttributes redirectAttributes) {
        try {
            String avatarUrl = userService.uploadAvatar(file, userId);
            redirectAttributes.addFlashAttribute("success", "Avatar updated successfully!");
            return "redirect:/profile"; // Chuyển hướng về trang profile
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload avatar.");
            return "redirect:/profile";
        }
    }
}