package org.example.bookingbe.controller;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class ClientPageController {


    @Autowired
    private IRoomService roomService;


    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
    //HomePage
    @GetMapping("/homePage")
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
    public String showAvailableRooms(Model model) {
        List<Room> availableRooms = roomService.getAvailableRooms();
        model.addAttribute("rooms", availableRooms);
        return "client/roomsPage";
    }


    //hotelManagerPage
    @GetMapping("/managerHotel")
    public String managerDashboardPage() {
        return "managerHotel/managerDashboard";
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
