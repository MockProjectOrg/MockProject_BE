package org.example.bookingbe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class PageController {

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
    @GetMapping("/adminHotel")
    public String adminDashboardPage() {
        return "adminHotel/adminDashboard";
    }
    @GetMapping("/adminManager")
    public String adminManagerPage() {
        return "adminHotel/adminManagerRoom";
    }

    @GetMapping("/roomDetail")
    public String roomDetailPage() {
        return "client/roomDetail";
    }

    @GetMapping("/rooms")
    public String roomsPage() {
        return "client/roomsPage";
    }


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
