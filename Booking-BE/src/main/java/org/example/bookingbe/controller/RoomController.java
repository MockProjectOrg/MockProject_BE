package org.example.bookingbe.controller;

import ch.qos.logback.core.model.Model;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.service.RoomService.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @GetMapping("/room/{id}")
    public String getRoomDetail(@PathVariable Long id, Model models){
        Optional<Room> room = roomService.getRoomById(id);

    }

}
