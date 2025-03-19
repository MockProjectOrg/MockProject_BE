package org.example.bookingbe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.User;
import org.example.bookingbe.respone.MessageRespone;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.example.bookingbe.service.UserDetail.UserPriciple;
import org.example.bookingbe.service.UserService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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


    @GetMapping("/")
    public String login(HttpServletRequest request){
        if(request.getUserPrincipal() !=null){
            return "redirect:/api/user/home";
        }
        return "auth/login";
    }

    @PostMapping("/dologin")
    public String doLogin(@ModelAttribute("user") User user, HttpSession session, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPriciple userPrinciple = (UserPriciple) authentication.getPrincipal();

            // Lưu userId vào session
            session.setAttribute("userId", userPrinciple.getId());

            return "redirect:/api/user/userProfile";
        } catch (Exception e) {
            model.addAttribute("message", new MessageRespone("Invalid username or password"));
            return "auth/login";
        }
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
    public String doRegister(@ModelAttribute("user") User user, Model model) throws MessagingException, jakarta.mail.MessagingException {
        if(userService.existsUser(user.getUserName()) || userService.exstsEmail(user.getEmail())){
            model.addAttribute("message", new MessageRespone("User or Email already exists"));
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
    public String searchRooms(@RequestParam(required = false) Long hotelId,
                              @RequestParam(required = false) Long roomTypeId,
                              @RequestParam(required = false) Double minPrice,
                              @RequestParam(required = false) Double maxPrice,
                              Model model) {

        List<Room> rooms = roomService.searchRooms(hotelId, roomTypeId, minPrice, maxPrice); // Đúng
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
