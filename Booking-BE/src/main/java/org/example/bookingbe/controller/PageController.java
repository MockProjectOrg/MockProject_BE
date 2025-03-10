package org.example.bookingbe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class PageController {


    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
    //HomePage
    @GetMapping("/home")
    public String homePage() {
        return "client/homePage";
    }
    @GetMapping("/about")
    public String aboutPage() {
        return "client/aboutPage";
    }
    @GetMapping("/contact")
    public String contactPage() {
        return "client/contactPage";
    }
    @GetMapping("/rooms")
    public String roomsPage() {
        return "client/roomsPage";
    }


    //hotelManagerPage
    @GetMapping("/managerHotel")
    public String managerDashboardPage() {
        return "managerHotel/managerDashboard";
    }

//    @GetMapping("/managerRooms")
//    public String managerRoomsPage(@RequestParam(required = false) Long userId) {
//        if (userId == null) {
//            return "redirect:/login"; // Chuyển về login nếu thiếu userId
//        }
//        return "redirect:/managerRooms?userId=" + userId; // Đảm bảo userId được truyền khi load trang
//    }





    //Authen
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }



}
