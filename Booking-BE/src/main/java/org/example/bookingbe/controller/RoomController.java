package org.example.bookingbe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.example.bookingbe.service.RoomService.RoomService;

@Controller
@RequestMapping("/api/room")
public class RoomController {
  private final RoomService roomService;
  public RoomController(RoomService roomService){
    this.roomService = roomService;
  }
  @GetMapping("/room/{id}")
  public String getRoomById(@PathVariable Long id, Model model){
    Optional<Room> room = roomService.getRoomById(id);
    if(room.isPresent()){
    model.addAttribute("room",room.get());
    return "room_detail";
  } return "Failed Page";
}
