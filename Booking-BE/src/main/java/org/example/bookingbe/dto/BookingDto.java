package org.example.bookingbe.dto;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class BookingDto {
    private Optional<Hotel> hotel;
    private Optional<Room> room;
    private UserDto user;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String description;


    public BookingDto( Optional<Hotel> hotel, Optional<Room> room, UserDto user, LocalDateTime checkIn, LocalDateTime checkOut, String description) {
        this.hotel = hotel;
        this.room = room;
        this.user = user;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.description = description;
    }

    public BookingDto(LocalDateTime checkIn, LocalDateTime checkOut, String description) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.description = description;
    }

    public Optional<Hotel> getHotel() {
        return hotel;
    }

    public void setHotel(Optional<Hotel> hotel) {
        this.hotel = hotel;
    }

    public Optional<Room> getRoom() {
        return room;
    }

    public void setRoom(Optional<Room> room) {
        this.room = room;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
