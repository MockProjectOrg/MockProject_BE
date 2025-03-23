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
import org.example.bookingbe.service.UserDetail.UserPriciple;
import org.example.bookingbe.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IRoomRepo roomRepo;


    @GetMapping("/")
    public String login(HttpServletRequest request){
        if(request.getUserPrincipal() !=null){
            return "redirect:/api/user/home";
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() !=null){
            return "redirect:/api/user/home";
        }
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/Doregister")
    public String doRegister(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) throws MessagingException {
        if(userService.existsUser(user.getUserName()) || userService.exstsEmail(user.getEmail())){
            model.addAttribute("message", new MessageRespone("User or Email already exists"));
            return "auth/register";
        }
        if(result.hasErrors()){
            return "auth/register";
        }
        userService.registerUser(user);
        return "redirect:auth/login";
    }

    @GetMapping("/user/userProfile")
    public String getUser(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/api/login"; // Nếu không có userId, yêu cầu đăng nhập lại
        }

        // Lấy User từ database
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/api/login";
        }

        model.addAttribute("user", user);
        return "profile"; // Trả về trang profile.html với dữ liệu user
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

        // Cập nhật thông tin
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAddress(updatedUser.getAddress());

        userService.saveUser(existingUser);  // Lưu user đã cập nhật

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

        // Nếu có điều kiện tìm kiếm -> lọc danh sách phòng theo điều kiện
        if (hotelName != null || typeName != null || minPrice != null || maxPrice != null || checkIn != null || checkOut != null ) {
            rooms = roomService.searchRooms(hotelName, typeName, minPrice, maxPrice, checkIn, checkOut)
                    .stream()
                    .filter(room -> room.getStatus() != null && room.getStatus().getId() == 1) // Chỉ lấy phòng available
                    .collect(Collectors.toList());
        } else {
            // Nếu không có điều kiện tìm kiếm -> chỉ lấy danh sách phòng available
            rooms = roomService.getAvailableRooms();
        }

        // Nếu có checkIn và checkOut -> Lọc phòng trống trong khoảng thời gian đó
        if (checkIn != null && checkOut != null) {
            rooms = rooms.stream()
                    .filter(room -> roomRepo.isRoomAvailable(room.getId(), checkIn, checkOut))
                    .collect(Collectors.toList());
        }

        model.addAttribute("rooms", rooms);
        return "client/searchRooms";
    }



    @GetMapping("/user/home")
    public String getUser(){
        return "client/homePage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/api/login";
    }

}
