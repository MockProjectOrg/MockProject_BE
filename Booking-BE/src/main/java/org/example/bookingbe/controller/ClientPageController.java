package org.example.bookingbe.controller;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Image;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.service.HotelService.IHotelService;
import org.example.bookingbe.service.ImageService.IImageService;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class ClientPageController {

    @Autowired
    private IImageService imageService;
    @Autowired
    private IRoomService roomService;

    @Autowired
    private IHotelService hotelService;
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
    //HomePage
    @GetMapping("/home")
    public String homePage(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels();
        Map<Long, List<String>> roomImages = new HashMap<>();

        // Tạo Map chứa danh sách phòng trống theo từng khách sạn
        Map<Long, List<Room>> availableRoomsMap = new HashMap<>();
        for (Hotel hotel : hotels) {
            List<Room> availableRooms = roomService.getAvailableRoomsByHotel(hotel.getId());
            availableRoomsMap.put(hotel.getId(), roomService.getAvailableRoomsByHotel(hotel.getId()));
            for (Room room : availableRooms) {
                List<String> images = imageService.getImagesByRoomId(room.getId()); // Gọi service để lấy danh sách ảnh
                roomImages.put(room.getId(), images);
            }
        }
        model.addAttribute("images", roomImages);
        model.addAttribute("hotels", hotels);

        model.addAttribute("availableRoomsMap", availableRoomsMap); // Truyền thêm danh sách phòng vào model
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
