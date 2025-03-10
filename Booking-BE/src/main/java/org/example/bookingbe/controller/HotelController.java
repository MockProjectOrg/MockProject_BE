package org.example.bookingbe.controller;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.service.HotelService.IHotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    private final IHotelService hotelService;

    public HotelController(IHotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public String showHotelList(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Hotel> hotels;

        if (search != null && !search.trim().isEmpty()) {
            hotels = hotelService.searchHotels(search);
        } else {
            hotels = hotelService.getAllHotels();
        }

        model.addAttribute("hotels", hotels);
        return "hotel";
    }

    @PostMapping("/add")
    public String addHotel(@ModelAttribute Hotel hotel) {
        hotelService.saveHotel(hotel);
        return "redirect:/hotels";
    }
}
