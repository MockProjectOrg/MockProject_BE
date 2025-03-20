package org.example.bookingbe.controller;

import org.example.bookingbe.model.Room;
import org.example.bookingbe.service.RoomService.IRoomService;
import org.example.bookingbe.service.RoomService.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/room")
public class RoomController {

    private final IRoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public String getRoomDetail(@PathVariable Long id, Model model) {
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        return "/client/room_detail";
    }
}
