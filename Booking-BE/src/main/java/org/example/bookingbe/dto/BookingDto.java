package org.example.bookingbe.dto;

import org.example.bookingbe.model.Hotel;
import org.example.bookingbe.model.Room;
import org.example.bookingbe.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class BookingDto {
    private Room roomId;
    private User userId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Boolean status;


    public BookingDto(Room roomId, User userId, LocalDateTime checkIn, LocalDateTime checkOut, Boolean status) {
        this.roomId = roomId;
        this.userId = userId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
