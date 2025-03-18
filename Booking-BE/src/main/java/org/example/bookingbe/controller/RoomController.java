package org.example.bookingbe.controller;


import org.example.bookingbe.model.Room;
import org.example.bookingbe.respone.MessageRespone;
import org.example.bookingbe.service.RoomService.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/hotel/{id}")
    @ResponseBody
    public ResponseEntity<?> getRoomByHotelId(@PathVariable Long id) {
        List<Room> rooms = roomService.getRoomsByHotel(id);
        if (rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageRespone("Not Found Room Of Hotel"));
        }
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public String viewRoomDetails(@PathVariable Long id, Model model, Principal principal) {
        Room room = roomService.getRoomById(id);
        if (room == null) {
            model.addAttribute("error", "Room not found!");
            return "error-page";
        }

        model.addAttribute("room", room);
        model.addAttribute("hotel", room.getHotel());

        if (principal != null && isAdminHotelOfRoom(principal.getName(), room)) {
            return "adminHotel/admin_room_detail";
        }
        return "client/auth/room_detail";
    }

    private boolean isAdminHotelOfRoom(String username, Room room) {
        return room.getHotel() != null
                && room.getHotel().getUser() != null
                && room.getHotel().getUser().getUserName().equals(username);
    }
}