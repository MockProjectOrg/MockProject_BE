package org.example.bookingbe.controller;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.service.HotelService.IHotelService;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final IRoomService roomService;
    private final IHotelService hotelService; // Inject IHotelService

    public RoomController(IRoomService roomService, IHotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    @GetMapping
    public String listRooms(@RequestParam(name = "minPrice", required = false) Double minPrice,
                            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
                            @RequestParam(name = "description", required = false) String description,
                            Model model) {
        List<Room> rooms;

        if ((minPrice != null && maxPrice != null) || (description != null && !description.trim().isEmpty())) {
            rooms = roomService.searchRooms(minPrice, maxPrice, description); // CẬP NHẬT
        } else {
            rooms = roomService.findAll();
        }

        model.addAttribute("rooms", rooms);
        return "room-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "room-form";
    }

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute Room room) {
        roomService.save(room);
        return "redirect:/rooms";
    }
}
