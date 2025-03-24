package org.example.bookingbe.controller;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.User;
import org.example.bookingbe.service.AdminService.IAdminService;
import org.example.bookingbe.service.MailSender.MailRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private MailRegister emailService;

    @GetMapping("/home")
    public String adminHome(Model model, Principal principal) {
        List<User> users = adminService.getAllUsers();
        List<Hotel> hotels = adminService.getAllHotels();

        if (hotels == null) {
            hotels = new ArrayList<>();
        }

        model.addAttribute("users", users);
        model.addAttribute("hotels", hotels);
        model.addAttribute("adminName", principal.getName());
        return "admin/adminPage";
    }

    @PostMapping("/updateRole")
    public String updateUserRole(@RequestParam Long userId, @RequestParam String newRole) {
        adminService.updateUserRole(userId, newRole);
        return "redirect:/admin/home";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user) {
        adminService.updateUser(user);
        return "redirect:/admin/home";
    }

    // 🔹 Quản lý Hotel (Chỉnh sửa lại để hiển thị trên adminPage)
    @PostMapping("/hotel/add")
    @ResponseBody
    public String addHotel(@RequestBody Hotel hotel) {
        adminService.saveHotel(hotel);
        return "OK";
    }


    @PostMapping("/hotel/update")
    @ResponseBody
    public String updateHotel(@RequestBody Hotel hotel) {
        Hotel existingHotel = adminService.getHotelById(hotel.getId());
        if (existingHotel == null) {
            return "Hotel not found!";
        }

        // Giữ nguyên giá trị user_id nếu không được gửi từ frontend
        if (hotel.getUser() == null) {
            hotel.setUser(existingHotel.getUser());
        }

        adminService.updateHotel(hotel);
        return "OK";
    }


    @DeleteMapping("/hotel/delete/{id}")
    @ResponseBody
    public String deleteHotel(@PathVariable Long id) {
        adminService.deleteHotel(id);
        return "OK";
    }

}
